package com.swapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.model.UserCollectible;

public interface UserCollectibleRepository extends JpaRepository<UserCollectible, Long>{

}
