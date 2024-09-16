package com.tanish.jwtAuthenticationProject.service.impl;

import com.tanish.jwtAuthenticationProject.dto.ApiResponse;
import com.tanish.jwtAuthenticationProject.dto.LoginRequest;
import com.tanish.jwtAuthenticationProject.dto.LoginResponse;
import com.tanish.jwtAuthenticationProject.dto.UserDto;
import com.tanish.jwtAuthenticationProject.entity.User;
import com.tanish.jwtAuthenticationProject.enums.UserRole;
import com.tanish.jwtAuthenticationProject.exception.InvalidCredentialsException;
import com.tanish.jwtAuthenticationProject.exception.NotFoundException;
import com.tanish.jwtAuthenticationProject.mapper.EntityDtoMapper;
import com.tanish.jwtAuthenticationProject.repository.UserRepo;
import com.tanish.jwtAuthenticationProject.security.JwtUtils;
import com.tanish.jwtAuthenticationProject.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;
    @Override
    public ApiResponse<UserDto> registerUser(UserDto registrationRequest) {
        UserRole role = UserRole.USER;

        if (registrationRequest.getRole() != null && registrationRequest.getRole().equalsIgnoreCase("admin")) {
            role = UserRole.ADMIN;
        }

        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .phoneNumber(registrationRequest.getPhoneNumber())
                .role(role)
                .build();

        User savedUser = userRepo.save(user);
        System.out.println(savedUser);

        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);
        return ApiResponse.<UserDto>builder()
                .status(200)
                .message("User Successfully Added")
                .data(userDto)
                .build();
    }

    @Override
    public ApiResponse<LoginResponse> loginUser(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new NotFoundException("Email not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password does not match");
        }

        String token = jwtUtils.generateToken(user);
        user.setPassword(null);
        user.setPhoneNumber(null);
        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(user);

        return ApiResponse.<LoginResponse>builder()
                .status(200)
                .message("User Successfully Logged In")
                .data(LoginResponse.builder()
                        .expirationTime("1 day")
                        .token(token)
                        .user(userDto)
                        .build())
                .build();
    }

    @Override
    public ApiResponse<List<UserDto>> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();

        return ApiResponse.<List<UserDto>>builder()
                .status(200)
                .message("All users fetched successfully")
                .data(userDtos)
                .build();
    }
    @Override
    public ApiResponse<UserDto> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String  email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not found"));
        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(user);
        return ApiResponse.<UserDto>builder()
                .status(200)
                .message("User fetched successfully")
                .data(userDto)
                .build();
    }

    @Override
    public ApiResponse<User> getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String  email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not found"));
        return ApiResponse.<User>builder()
                .status(200)
                .message("User fetched successfully")
                .data(user)
                .build();
    }

}
