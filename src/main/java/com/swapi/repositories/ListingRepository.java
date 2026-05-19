package com.swapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.model.Listing;

public interface ListingRepository extends JpaRepository<Listing, Long>{

}
