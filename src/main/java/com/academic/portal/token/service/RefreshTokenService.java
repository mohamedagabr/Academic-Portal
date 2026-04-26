package com.academic.portal.token.service;

import com.academic.portal.entity.User;
import com.academic.portal.entity.RefreshToken;

public interface RefreshTokenService {
     RefreshToken createRefreshToken(User user);
    boolean validateRefreshToken(String token);
    void deleteByUsername(String username);
}
