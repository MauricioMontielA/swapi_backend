package com.swapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.model.TradeParticipant;

public interface TradeParticipantRepository extends JpaRepository<TradeParticipant, Long>{

}
