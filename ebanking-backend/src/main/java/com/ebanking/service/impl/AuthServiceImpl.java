package com.ebanking.service.impl;

import com.ebanking.constant.RoleType;
import com.ebanking.dto.*;
import com.ebanking.entity.RefreshToken;
import com.ebanking.entity.Role;
import com.ebanking.entity.User;
import com.ebanking.exception.EmailAlreadyExistsException;
import com.ebanking.exception.ResourceNotFoundException;
import com.ebanking.repository.RoleRepository;
import com.ebanking.repository.UserRepository;
import com.ebanking.security.JwtService;
import com.ebanking.service.AuthService;
import com.ebanking.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new EmailAlreadyExistsException(
                    "Email already registered"
            );
        }

        Role role = roleRepository.findByRoleName(
                        RoleType.ROLE_CUSTOMER
                )
                .orElseThrow(() ->
                        new RuntimeException("Default role not found"));

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(role);

        userRepository.save(user);

        String accessToken =
                jwtService.generateAccessToken(user.getEmail());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(900000L)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String accessToken =
                jwtService.generateAccessToken(user.getEmail());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(900000L)
                .build();
    }

    @Override
    public AuthResponse refreshToken(
            RefreshTokenRequest request
    ) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken()
                );

        User user = refreshToken.getUser();

        String accessToken =
                jwtService.generateAccessToken(
                        user.getEmail()
                );

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(900000L)
                .build();
    }
    @Override
    public void logout(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        refreshTokenService.deleteByUser(user);
    }


}