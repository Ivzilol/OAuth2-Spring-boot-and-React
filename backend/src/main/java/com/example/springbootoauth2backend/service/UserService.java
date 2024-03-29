package com.example.springbootoauth2backend.service;

import com.example.springbootoauth2backend.model.entity.UserEntity;
import com.example.springbootoauth2backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public void saveUser(UserEntity user) {
        this.userRepository.save(user);
    }
}
