package com.academic.portal.token.service.impl;

import com.academic.portal.entity.User;
import com.academic.portal.entity.RefreshToken;
import com.academic.portal.common.exception.BusinessException;
import com.academic.portal.common.exception.ErrorCode;
import com.academic.portal.token.repository.RefreshTokenRepository;
import com.academic.portal.security.JwtService;
import com.academic.portal.token.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService ;

    @Override
    public RefreshToken createRefreshToken(User user) {

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);

        if (existingToken.isPresent()) {
            refreshTokenRepository.delete(existingToken.get());
            refreshTokenRepository.flush();
        }
        String refreshJwt = jwtService.generateRefreshToken(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(refreshJwt)
//                .expiryDate(Instant.now().plus(10, ChronoUnit.MINUTES))
                .expiryDate(jwtService.extractExpiration(refreshJwt).toInstant())
                .build();
        user.getRefreshTokens().clear();
        user.getRefreshTokens().add(refreshToken);
        return refreshTokenRepository.save(refreshToken);
    }


    @Override
    public boolean validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN));
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return true;
    }


    @Override
    @Transactional
    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteByUser_Username(username);
    }
}
