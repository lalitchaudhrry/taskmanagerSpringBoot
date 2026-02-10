package com.slayers.taskmanager.service.impl;

import com.slayers.taskmanager.dto.*;
import com.slayers.taskmanager.entity.User;
import com.slayers.taskmanager.exception.ResourceNotFoundException;
import com.slayers.taskmanager.exception.UserAlreadyExistsException;
import com.slayers.taskmanager.repository.UserRepository;
import com.slayers.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // ✅ REGISTER
    @Override
    public UserResponse register(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // ⚠️ plain text (temporary)
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(userRepository.save(user));
    }

    // ✅ LOGIN
    @Override
    public UserResponse login(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // plain password check
        if (!request.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return mapToResponse(user);
    }

    // ✅ GET ALL USERS
    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UserResponse mapToResponse(User user){

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
