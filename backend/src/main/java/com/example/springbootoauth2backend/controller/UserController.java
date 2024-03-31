package com.example.springbootoauth2backend.controller;

import com.example.springbootoauth2backend.model.entity.UserEntity;
import com.example.springbootoauth2backend.model.entity.UserRole;
import com.example.springbootoauth2backend.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/{email}")
    public void changeToAdmin(@PathVariable String email) {
        Optional<UserEntity> userEntity = userService.findByEmail(email);
        userEntity.ifPresent(entity -> entity.setRole(UserRole.ROLE_ADMIN));
        userEntity.ifPresent(this.userService::saveUser);
    }
}
