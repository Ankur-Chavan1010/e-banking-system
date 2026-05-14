package com.ebanking.controller;

import com.ebanking.dto.*;
import com.ebanking.response.ApiResponse;
import com.ebanking.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(authService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successful")
                .data(authService.login(request))
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Access token refreshed successfully")
                .data(authService.refreshToken(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(
            Authentication authentication
    ) {

        authService.logout(authentication.getName());

        return ApiResponse.<String>builder()
                .success(true)
                .message("Logout successful")
                .data("User logged out successfully")
                .build();
    }

}