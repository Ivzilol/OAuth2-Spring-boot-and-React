package com.example.springbootoauth2backend.config;


import com.example.springbootoauth2backend.model.entity.RegistrationSource;
import com.example.springbootoauth2backend.model.entity.UserEntity;
import com.example.springbootoauth2backend.model.entity.UserRole;
import com.example.springbootoauth2backend.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;

    public OAuth2LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if ("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())
                || "google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String email = attributes.getOrDefault("email", "").toString();
            String name = attributes.getOrDefault("name", "").toString();
            Optional<UserEntity> user = this.userService.findByEmail(email);
            if (user.isPresent()) {
                authNewUser(user.get(), attributes, user.get(), oAuth2AuthenticationToken);
            } else {
                saveNewUser(oAuth2AuthenticationToken, attributes, email, name);
            }
        }
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:3000");
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private static void authNewUser(UserEntity user, Map<String, Object> attributes, UserEntity user1, OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        if (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().equals("github")) {
            DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                    attributes, "id");
            Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user1.getRole().name())),
                    oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
            SecurityContextHolder.getContext().setAuthentication(securityAuth);
        }
        if (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().equals("google")) {
            DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                    attributes, "sub");
            Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user1.getRole().name())),
                    oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
            SecurityContextHolder.getContext().setAuthentication(securityAuth);
        }
    }

    private void saveNewUser(OAuth2AuthenticationToken oAuth2AuthenticationToken, Map<String, Object> attributes, String email, String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setRole(UserRole.ROLE_USER);
        userEntity.setEmail(email);
        userEntity.setName(name);
        if (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().equals("github")) {
            userEntity.setSource(RegistrationSource.GITHUB);
        }
        if (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().equals("google")) {
            userEntity.setSource(RegistrationSource.GOOGLE);
        }
        userService.saveUser(userEntity);
        authNewUser(userEntity, attributes, userEntity, oAuth2AuthenticationToken);
    }
}
