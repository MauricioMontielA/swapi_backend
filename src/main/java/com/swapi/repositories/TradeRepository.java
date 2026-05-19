package com.swapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.model.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long>{

}
