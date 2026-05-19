package com.swapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.model.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long>{
	
    Optional<Collection> findByCode(String code);

}
