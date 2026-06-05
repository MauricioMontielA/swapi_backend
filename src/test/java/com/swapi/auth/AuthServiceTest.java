package com.swapi.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.swapi.auth.dto.AuthProvider;
import com.swapi.auth.dto.AuthResponse;
import com.swapi.auth.dto.RegisterRequest;
import com.swapi.auth.service.AuthService;
import com.swapi.auth.service.JWTService;
import com.swapi.auth.service.RefreshTokenService;
import com.swapi.model.auxiliar.Badge;
import com.swapi.user.User;
import com.swapi.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@Mock
	UserRepository userRepo;
	@Mock
	JWTService jwtService;
	@Mock
	RefreshTokenService refreshTokenService;
	@Mock
    PasswordEncoder passwordEncoder;
	@Mock
	AuthenticationManager authenticationManager;
	
	@InjectMocks
	AuthService authService;
	
	@Test
	void shouldReturnTokensWhenUserRegister() {
		RegisterRequest register = new RegisterRequest();
		register.setEmail("test@test.com");
		register.setPassword("12345");
		register.setUsername("test");
		
		when(userRepo.existsByEmail("test@test.com"))
			.thenReturn(false);
		
		when(passwordEncoder.encode("12345"))
			.thenReturn("HASH");
		
		when(jwtService.generateAccessToken(any(User.class)))
			.thenReturn("TOKEN-BEARER");
		
		when(refreshTokenService.createRefreshToken(any(User.class)))
			.thenReturn("TOKEN-REFRESH");
		
		AuthResponse response = authService.register(register);
		
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		
		verify(userRepo).save(userCaptor.capture());
		
		User savedUser = userCaptor.getValue();
		
		assertEquals("test@test.com", savedUser.getEmail());
		assertEquals("HASH", savedUser.getPasswordHash());
		assertNotEquals("123456", savedUser.getPasswordHash());
		assertEquals("test", savedUser.getUsername());
		assertEquals(AuthProvider.LOCAL, savedUser.getAuthProvider());
		assertEquals(Badge.ROOKIE, savedUser.getBadge());
		assertNull(savedUser.getProviderUserId());
		assertEquals(-1, savedUser.getRating());

		assertEquals("TOKEN-BEARER", response.getAccessToken());
		assertEquals("TOKEN-REFRESH", response.getRefreshToken());

	}
	
	@Test
	void shouldThrowRunTimeExceptionWhenEmailIsAlreadyRegistered() {
		RegisterRequest register = new RegisterRequest();
		register.setEmail("test@test.com");
		register.setPassword("12345");
		register.setUsername("test");
		
		when(userRepo.existsByEmail("test@test.com"))
			.thenReturn(true);
		
		assertThrows(RuntimeException.class, 
				() -> authService.register(register));

	}
	
	
}
