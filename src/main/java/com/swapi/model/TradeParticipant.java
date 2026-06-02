package com.swapi.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swapi.model.auxiliar.TradeParticipantStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "sp_tradeParticipant")
public class TradeParticipant extends RecBase{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trade_id")
	private Trade trade;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
    @Enumerated(EnumType.STRING)
	private TradeParticipantStatus status;
	
	@OneToMany(mappedBy = "fromParticipant")
	Set<TradeItem> tradeItemsFrom = new HashSet<>();
	
	@OneToMany(mappedBy = "toParticipant")
	Set<TradeItem> tradeItemsTo = new HashSet<>();
}
