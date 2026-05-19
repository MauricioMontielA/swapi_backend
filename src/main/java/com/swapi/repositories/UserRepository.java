package com.swapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
}
