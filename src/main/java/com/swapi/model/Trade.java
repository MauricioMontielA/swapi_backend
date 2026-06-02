package com.swapi.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swapi.model.auxiliar.TradeStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sp_trade")
public class Trade extends RecBase{
    @Enumerated(EnumType.STRING)
	private TradeStatus status;
	
	@OneToMany(mappedBy = "trade")
	Set<TradeItem> tradeItems = new HashSet<>();
	
	@OneToMany(mappedBy = "trade")
	Set<TradeParticipant> tradeParticipants = new HashSet<>();
}
