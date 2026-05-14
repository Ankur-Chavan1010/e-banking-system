package com.ebanking.service.impl;

import com.ebanking.entity.RefreshToken;
import com.ebanking.entity.User;
import com.ebanking.exception.ResourceNotFoundException;
import com.ebanking.repository.RefreshTokenRepository;
import com.ebanking.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByUser(user)
                .orElse(new RefreshToken());

        refreshToken.setUser(user);

        refreshToken.setToken(
                java.util.UUID.randomUUID().toString()
        );

        refreshToken.setExpiryDate(
                LocalDateTime.now()
                        .plusSeconds(refreshTokenExpiration / 1000)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Refresh token not found"
                        ));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    @Override
    public void deleteByUser(User user) {

        refreshTokenRepository.deleteByUser(user);
    }
}