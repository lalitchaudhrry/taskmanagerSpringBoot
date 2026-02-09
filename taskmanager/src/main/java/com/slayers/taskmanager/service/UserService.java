package com.slayers.taskmanager.service;

import com.slayers.taskmanager.dto.UserLoginRequest;
import com.slayers.taskmanager.dto.UserRegisterRequest;
import com.slayers.taskmanager.dto.UserResponse;

public interface UserService {

    UserResponse register(UserRegisterRequest request);

    UserResponse login(UserLoginRequest request);
}
