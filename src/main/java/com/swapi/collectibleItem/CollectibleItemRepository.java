package com.swapi.collectibleItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectibleItemRepository extends JpaRepository<CollectibleItem, Long>{
	List<CollectibleItem> findByCollectionId(Long id);
}
