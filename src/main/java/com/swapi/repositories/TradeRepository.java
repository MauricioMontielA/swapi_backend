package com.swapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swapi.model.Trade;
import com.swapi.model.TradeParticipant;
import com.swapi.model.auxiliar.TradeParticipantStatus;
import com.swapi.model.auxiliar.TradeStatus;

public interface TradeRepository extends JpaRepository<Trade, Long> {

	@Query("""
			    SELECT DISTINCT t.id
			    FROM Trade t
			    JOIN t.tradeParticipants tp
			    WHERE tp.status = :participantStatus
			    AND tp.user.id = :userId
			    AND t.status NOT IN :tradeStatuses
			""")
	Page<Long> findTradeIdsForPartStatusAndUser(TradeParticipantStatus participantStatus, Long userId, List<TradeStatus> tradeStatuses,
			Pageable pageable);

	@Query("""
			    SELECT DISTINCT t.id
			    FROM Trade t
			    JOIN t.tradeParticipants tp
			    WHERE t.status = :tradeStatus
			    AND tp.user.id = :userId
			""")
	Page<Long> findTradeIdsForStatusAndUser(TradeStatus tradeStatus, Long userId, Pageable pageable);

	@EntityGraph(attributePaths = { "tradeParticipants", "tradeParticipants.user", "tradeItems",
			"tradeItems.userCollectible", "tradeItems.userCollectible.collectibleItem" })
	@Query("""
			    SELECT DISTINCT t
			    FROM Trade t
			    WHERE t.id IN :ids
			""")
	List<Trade> findTradesWithDetailsByIdIn(List<Long> ids);

}
