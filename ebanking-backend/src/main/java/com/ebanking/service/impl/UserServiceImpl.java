package com.ebanking.service.impl;

import com.ebanking.dto.UserResponse;
import com.ebanking.entity.User;
import com.ebanking.repository.UserRepository;
import com.ebanking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .role(user.getRole().getRoleName().name())
                .build();
    }
}