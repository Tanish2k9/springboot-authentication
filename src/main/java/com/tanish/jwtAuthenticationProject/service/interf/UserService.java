package com.tanish.jwtAuthenticationProject.service.interf;

import com.tanish.jwtAuthenticationProject.dto.ApiResponse;
import com.tanish.jwtAuthenticationProject.dto.LoginRequest;
import com.tanish.jwtAuthenticationProject.dto.UserDto;
import com.tanish.jwtAuthenticationProject.entity.User;

public interface UserService {
    ApiResponse registerUser(UserDto registrationRequest);
    ApiResponse loginUser(LoginRequest loginRequest);
    ApiResponse getAllUsers();

    ApiResponse<UserDto> getUserProfile();

    ApiResponse<User> getLoginUser();
}
