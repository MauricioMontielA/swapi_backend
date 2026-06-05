package com.swapi.collection;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long>{
	
    Optional<Collection> findByCode(String code);

}
