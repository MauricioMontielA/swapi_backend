package com.swapi.userCollectible;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.swapi.trade.Trade;
import com.swapi.userCollectible.dto.UserCollectibleMatchRepoDto;
import com.swapi.userCollectible.dto.UserCollectibleCollectionViewDto;

public interface UserCollectibleRepository
		extends JpaRepository<UserCollectible, Long>, JpaSpecificationExecutor<UserCollectible> {
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

	@EntityGraph(attributePaths = { "user" })
	List<UserCollectible> findByIdIn(List<Long> ids);

//	@Query("""
//		    SELECT DISTINCT t
//		    FROM Trade t
//		    WHERE t.id IN :ids
//		""")
	@EntityGraph(attributePaths = { 
			"collectibleItem", 
			"collectibleItem.collection" 
	})
	List<UserCollectible> findByUserIdAndCollectibleItemIdIn(Long userId, List<Long> collectibleItemId);

	@Query(value = """
			SELECT
				sci.id,
				sci.number,
				CASE
					WHEN suc.id IS NOT NULL THEN TRUE
					ELSE FALSE
				END AS owned,
				sci.image_url
			FROM
				sp_collectibleItem sci
			LEFT JOIN sp_userCollectible suc ON
				suc.collectible_item_id = sci.id AND suc.user_id = :userId
			WHERE sci.collection_id  = :collectionId
						""", nativeQuery = true)
	List<UserCollectibleCollectionViewDto> findOwnershipByUserAndCollection(Long userId, Long collectionId);

	@Query(value = """
			SELECT
			    u.id AS userId,
			    u.username AS username,
			    COUNT(DISTINCT my_duplicates.collectible_item_id) AS missingFromMyOfferCount,
			    COUNT(DISTINCT candidate_duplicates.collectible_item_id) AS usefulOfferCount
			FROM sp_user u
			LEFT JOIN sp_userCollectible my_duplicates
			    ON my_duplicates.user_id = :userId
			    AND my_duplicates.quantity > 1
			    AND EXISTS (
			        SELECT 1
			        FROM sp_collectibleItem ci
			        WHERE ci.id = my_duplicates.collectible_item_id
			          AND ci.collection_id = :collectionId
			    )
			    AND NOT EXISTS (
			        SELECT 1
			        FROM sp_userCollectible candidate_has
			        WHERE candidate_has.user_id = u.id
			          AND candidate_has.collectible_item_id = my_duplicates.collectible_item_id
			          AND candidate_has.quantity > 0
			    )
			LEFT JOIN sp_userCollectible candidate_duplicates
			    ON candidate_duplicates.user_id = u.id
			    AND candidate_duplicates.quantity > 1
			    AND EXISTS (
			        SELECT 1
			        FROM sp_collectibleItem ci
			        WHERE ci.id = candidate_duplicates.collectible_item_id
			          AND ci.collection_id = :collectionId
			    )
			    AND NOT EXISTS (
			        SELECT 1
			        FROM sp_userCollectible my_has
			        WHERE my_has.user_id = :userId
			          AND my_has.collectible_item_id = candidate_duplicates.collectible_item_id
			          AND my_has.quantity > 0
			    )
			WHERE u.id <> :userId
			GROUP BY u.id, u.username
			HAVING
			    COUNT(DISTINCT my_duplicates.collectible_item_id) > 0
			    AND COUNT(DISTINCT candidate_duplicates.collectible_item_id) > 0
			ORDER BY
			    usefulOfferCount DESC,
			    missingFromMyOfferCount DESC
			""", nativeQuery = true)
	List<UserCollectibleMatchRepoDto> findBestMatchesForSwap(Long userId, Long collectionId);

	@Query(value = """
			SELECT
			    u.id,
			    u.username,
			    (:offerCount - COUNT(DISTINCT candidate_has.collectible_item_id))
			        AS missingFromMyOfferCount,
			    COUNT(DISTINCT candidate_duplicates.collectible_item_id)
			        AS usefulOfferCount
			FROM sp_user u
			LEFT JOIN sp_userCollectible candidate_has
			    ON candidate_has.user_id = u.id
			    AND candidate_has.collectible_item_id IN (:offeredItemIds)
			    AND candidate_has.quantity > 0
			LEFT JOIN sp_userCollectible candidate_duplicates
			    ON candidate_duplicates.user_id = u.id
			    AND candidate_duplicates.quantity > 1
			    AND EXISTS (
			        SELECT 1
			        FROM sp_collectibleItem ci
			        WHERE ci.id = candidate_duplicates.collectible_item_id
			          AND ci.collection_id = :collectionId
			    )
			    AND NOT EXISTS (
			        SELECT 1
			        FROM sp_userCollectible my_has
			        WHERE my_has.user_id = :userId
			          AND my_has.collectible_item_id = candidate_duplicates.collectible_item_id
			          AND my_has.quantity > 0
			    )
			WHERE u.id <> :userId
			GROUP BY u.id, u.username
			HAVING
			    (:offerCount - COUNT(DISTINCT candidate_has.collectible_item_id)) > 0
			    AND COUNT(DISTINCT candidate_duplicates.collectible_item_id) > 0
			ORDER BY usefulOfferCount DESC
						""", nativeQuery = true)
	List<UserCollectibleMatchRepoDto> findBestMatchesForSwapWithItemsIds(Long userId, Long collectionId,
			List<Long> offeredItemIds, int offerCount);

}
