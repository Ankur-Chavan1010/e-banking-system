package com.ebanking.service;

import com.ebanking.entity.RefreshToken;
import com.ebanking.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void deleteByUser(User user);
}