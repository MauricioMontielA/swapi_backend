package com.swapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.model.CollectibleItem;

public interface CollectibleItemRepository extends JpaRepository<CollectibleItem, Long>{
	List<CollectibleItem> findByCollectionId(Long id);
}
