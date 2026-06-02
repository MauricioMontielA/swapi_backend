package com.swapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swapi.model.TradeParticipant;

public interface TradeParticipantRepository extends JpaRepository<TradeParticipant, Long> {

	@EntityGraph(attributePaths = {"trade", "user"})
	Optional<TradeParticipant> findById(Long id);
}
