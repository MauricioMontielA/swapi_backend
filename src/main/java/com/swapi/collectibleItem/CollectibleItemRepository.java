package com.swapi.collectibleItem;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swapi.model.auxiliar.TradeStatus;

public interface CollectibleItemRepository extends JpaRepository<CollectibleItem, Long> {
	List<CollectibleItem> findByCollectionId(Long id);

	Page<CollectibleItem> findByCollectionId(Long collectionId, Pageable pageable);

	Page<CollectibleItem> findByCollectionIdAndNameContainingIgnoreCase(Long collectionId, String name,
			Pageable pageable);

	List<CollectibleItem> findByIdIn(List<Long> ids);

}
