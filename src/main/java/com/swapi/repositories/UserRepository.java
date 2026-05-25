package com.swapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.auth.dto.AuthProvider;
import com.swapi.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
	
	Optional<User> findByAuthProviderAndProviderUserId(
            AuthProvider authProvider,
            String providerUserId
    );
	
	Optional<User> findByEmail(String email);
}
