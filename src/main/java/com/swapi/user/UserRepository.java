package com.swapi.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swapi.auth.dto.AuthProvider;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);
	
	Optional<User> findByAuthProviderAndProviderUserId(
            AuthProvider authProvider,
            String providerUserId
    );
	
	Optional<User> findByEmail(String email);
	
	@Query(""" 
			SELECT 
				COUNT(DISTINCT t.id)
			FROM TradeParticipant tp
			JOIN tp.trade t
			WHERE t.status = com.swapi.model.auxiliar.TradeStatus.COMPLETED
			AND tp.user.id = :userId
			""")
	int getCountTradesByUser(Long userId);
	
	@Query(""" 
			SELECT 
				COUNT(DISTINCT ci.collection)
			FROM UserCollectible uc
			JOIN uc.collectibleItem ci
			WHERE uc.user.id = :userId
			""")
	int getCountCollectionsByUser(Long userId);
}
