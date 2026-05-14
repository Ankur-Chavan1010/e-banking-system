package com.ebanking.service;

import com.ebanking.dto.AuthResponse;
import com.ebanking.dto.LoginRequest;
import com.ebanking.dto.RefreshTokenRequest;
import com.ebanking.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void logout(String email);
}
