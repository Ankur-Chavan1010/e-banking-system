package com.ebanking.controller;

import com.ebanking.dto.UserResponse;
import com.ebanking.response.ApiResponse;
import com.ebanking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ApiResponse<UserResponse> getProfile() {

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User profile fetched successfully")
                .data(userService.getCurrentUser())
                .build();
    }
}