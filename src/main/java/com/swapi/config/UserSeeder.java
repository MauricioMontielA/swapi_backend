package com.swapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.swapi.model.User;
import com.swapi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createUserIfNotExists("admin@test.com", "Admin", "ADMIN");
        createUserIfNotExists("user1@test.com", "User 1", "USER");
        createUserIfNotExists("user2@test.com", "User 2", "USER");
        createUserIfNotExists("user3@test.com", "User 3", "USER");
    }

    private void createUserIfNotExists(String email, String username, String role) {
        if (userRepository.existsByEmail(email)) {
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode("123456"));
        user.setAuthProvider("LOCAL");
//        user.setRole(role);
//        user.setEnabled(true);

        userRepository.save(user);
    }
}
