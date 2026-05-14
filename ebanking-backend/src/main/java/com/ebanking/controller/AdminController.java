package com.ebanking.controller;

import com.ebanking.dto.UserResponse;
import com.ebanking.response.ApiResponse;
import com.ebanking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUsers() {

        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(userService.getAllUsers())
                .build();
    }
}