package com.swapi.auth.service;

import org.springframework.stereotype.Service;

import com.swapi.auth.dto.AuthProvider;
import com.swapi.auth.dto.AuthResponse;
import com.swapi.auth.dto.GoogleLoginRequest;
import com.swapi.auth.model.GoogleUserInfo;
import com.swapi.model.User;
import com.swapi.repositories.UserRepository;

@Service
public class GoogleAuthService {
	   private final UserRepository userRepository;
	    private final JWTService jwtService;
	    private final RefreshTokenService refreshTokenService;

	    public GoogleAuthService(
	            UserRepository userRepository,
	            JWTService jwtService,
	            RefreshTokenService refreshTokenService
	    ) {
	        this.userRepository = userRepository;
	        this.jwtService = jwtService;
	        this.refreshTokenService = refreshTokenService;
	    }

	    public AuthResponse loginWithGoogle(GoogleLoginRequest request) {

	        GoogleUserInfo googleUser = validateGoogleToken(request.getIdToken());

	        User user = userRepository
	                .findByAuthProviderAndProviderUserId(
	                        AuthProvider.GOOGLE,
	                        googleUser.getSub()
	                )
	                .orElseGet(() -> findOrCreateGoogleUser(googleUser));

	        String accessToken = jwtService.generateAccessToken(user);
	        String refreshToken = refreshTokenService.createRefreshToken(user);

	        return new AuthResponse(accessToken, refreshToken);
	    }

	    private User findOrCreateGoogleUser(GoogleUserInfo googleUser) {

	        return userRepository.findByEmail(googleUser.getEmail())
	                .map(existingUser -> {
	                    existingUser.setAuthProvider(AuthProvider.GOOGLE);
	                    existingUser.setProviderUserId(googleUser.getSub());
	                    existingUser.setUsername(googleUser.getName());
	                    existingUser.setProfileImageUrl(googleUser.getPicture());
	                    return userRepository.save(existingUser);
	                })
	                .orElseGet(() -> {
	                    User newUser = new User();
	                    newUser.setEmail(googleUser.getEmail());
	                    newUser.setUsername(googleUser.getName());
	                    newUser.setProfileImageUrl(googleUser.getPicture());
	                    newUser.setAuthProvider(AuthProvider.GOOGLE);
	                    newUser.setProviderUserId(googleUser.getSub());
//	                    newUser.setRole(Role.USER);
//	                    newUser.setEnabled(true);

	                    return userRepository.save(newUser);
	                });
	    }

	    private GoogleUserInfo validateGoogleToken(String idToken) {
	        throw new UnsupportedOperationException(
	                "Aquí debes validar el token real con Google"
	        );
	    }
}
