package com.ebanking.service;

import com.ebanking.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getCurrentUser();

    List<UserResponse> getAllUsers();
}