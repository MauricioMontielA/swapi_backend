package com.swapi.userCollectible;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserCollectibleRepository extends JpaRepository<UserCollectible, Long>, JpaSpecificationExecutor<UserCollectible>{
	List<UserCollectible> findByUserId(Long userId);
	
	@Query("""
		    SELECT uc
		    FROM UserCollectible uc
		    JOIN FETCH uc.collectibleItem
		    WHERE uc.user.id = :userId
		""")
	List<UserCollectible> findByUserIdWithItems(Long userId);
	
	@Query("""
		    SELECT uc
		    FROM UserCollectible uc
		    JOIN FETCH uc.collectibleItem ci
		    WHERE uc.user.id = :userId
		      AND ci.collection.id = :collectionId
		""")
	List<UserCollectible> findByUserIdAndCollectionIdWithItems(Long userId, Long collectionId);
	
	
	@EntityGraph(attributePaths = {"user"})
	List<UserCollectible> findByIdIn(List<Long> ids);
}
