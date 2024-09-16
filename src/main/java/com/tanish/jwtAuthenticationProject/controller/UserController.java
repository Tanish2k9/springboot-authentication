package com.tanish.jwtAuthenticationProject.controller;

import com.tanish.jwtAuthenticationProject.dto.ApiResponse;
import com.tanish.jwtAuthenticationProject.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/my-info")
    public ResponseEntity<ApiResponse> getUserInfoAndOrderHistory(){
        return ResponseEntity.ok(userService.getUserProfile());
    }
}
