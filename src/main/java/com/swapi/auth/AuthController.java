package com.swapi.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.AuthResponse;
import com.swapi.auth.dto.GoogleLoginRequest;
import com.swapi.auth.dto.LoginRequest;
import com.swapi.auth.dto.RefreshTokenRequest;
import com.swapi.auth.dto.RegisterRequest;
import com.swapi.auth.service.AuthService;
import com.swapi.auth.service.GoogleAuthService;
import com.swapi.auth.service.RefreshTokenService;
import com.swapi.collection.CollectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
    private final GoogleAuthService googleAuthService;
    private final RefreshTokenService refreshTokenService;



    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/google")
    public AuthResponse googleLogin(@RequestBody GoogleLoginRequest request) {
        return googleAuthService.loginWithGoogle(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        String newAccessToken = refreshTokenService.refreshAccessToken(
                request.getRefreshToken()
        );

        return new AuthResponse(newAccessToken, request.getRefreshToken());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.revokeRefreshToken(request.getRefreshToken());
    }
}
