package com.swapi.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.swapi.auth.model.RefreshToken;
import com.swapi.auth.repositories.RefreshTokenRepository;
import com.swapi.user.User;

import lombok.AllArgsConstructor;

@Service
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;
    private final JWTService jwtService;

    @Value("${app.jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;
    
    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            JWTService jwtService
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public String createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(
                LocalDateTime.now().plusSeconds(refreshTokenExpirationMs / 1000)
        );
        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

    public String refreshAccessToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return jwtService.generateAccessToken(refreshToken.getUser());
    }

    public void revokeRefreshToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
}
