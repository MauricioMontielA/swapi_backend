package com.swapi.collection;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swapi.collection.dto.CollectionAddDto;
import com.swapi.collection.dto.CollectionAddProgressDto;
import com.swapi.collection.dto.CollectionDetailedItemsDto;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

	Optional<Collection> findByCode(String code);

	@Query(value = """
			SELECT
			    sc.id,
			    sc.name,
			    '' description,
			    '' imageUrl,
			    ROUND(
			        COUNT(DISTINCT suc.collectible_item_id) * 100.0
			        / COUNT(DISTINCT sci.id),
			        2
			    ) AS progress
			FROM sp_collection sc
			JOIN sp_collectibleItem sci
			    ON sci.collection_id = sc.id
			LEFT JOIN sp_userCollectible suc
			    ON suc.collectible_item_id = sci.id
			    AND suc.user_id = :userId
			GROUP BY sc.id, sc.name
			HAVING COUNT(DISTINCT suc.collectible_item_id) > 0
			ORDER BY progress DESC
			""", nativeQuery = true)
	List<CollectionAddProgressDto> findCollectionsInProgressByUserId(Long userId);

	@Query(value = """
			SELECT
			    sc.id, 
			    sc.name,
			    '' AS description,
			    '' AS imageUrl
			FROM sp_collection sc
			JOIN sp_collectibleItem sci
			    ON sci.collection_id = sc.id
			LEFT JOIN sp_userCollectible suc
			    ON suc.collectible_item_id = sci.id
			    AND suc.user_id = :userId
			GROUP BY sc.id, sc.name
			HAVING COUNT(DISTINCT suc.collectible_item_id) = 0
			""", nativeQuery = true)
	List<CollectionAddDto> findRecommendedCollectionsByUserId(Long userId);

	@Query(value = """
			SELECT
			    sc.id,
			    sc.name,
			    sc.type,
			    ROUND(
			        COUNT(DISTINCT suc.collectible_item_id) * 100.0
			        / COUNT(DISTINCT sci.id),
			        2
			    ) AS progress,
			        (COUNT(DISTINCT sci.id) 
			        - COUNT(DISTINCT suc.collectible_item_id))
			     AS missing,
			     COUNT(
				    DISTINCT CASE
				        WHEN suc.quantity > 1
				        THEN suc.collectible_item_id
				    END
				) AS repeated,
				'' imageUrl
			FROM sp_collection sc
			JOIN sp_collectibleItem sci
			    ON sci.collection_id = sc.id
			LEFT JOIN sp_userCollectible suc
			    ON suc.collectible_item_id = sci.id
			    AND suc.user_id = :userId
			GROUP BY sc.id, sc.name, sc.type
			HAVING COUNT(DISTINCT suc.collectible_item_id) > 0
			ORDER BY progress DESC
			""", nativeQuery = true)
	List<CollectionDetailedItemsDto> findCollectionItemsDetailedByUserId(Long userId);

}
