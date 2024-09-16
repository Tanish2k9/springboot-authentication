package com.tanish.jwtAuthenticationProject.dto;

import com.tanish.jwtAuthenticationProject.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String expirationTime;
    private UserDto user;
}
