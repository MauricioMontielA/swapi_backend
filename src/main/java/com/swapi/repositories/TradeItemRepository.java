package com.swapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swapi.model.TradeItem;

public interface TradeItemRepository extends JpaRepository<TradeItem, Long>{

}
