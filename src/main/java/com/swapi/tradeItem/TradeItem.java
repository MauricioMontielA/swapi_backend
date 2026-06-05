package com.swapi.tradeItem;

import com.swapi.model.RecBase;
import com.swapi.trade.Trade;
import com.swapi.tradeParticipant.TradeParticipant;
import com.swapi.userCollectible.UserCollectible;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "sp_tradeItem")
public class TradeItem extends RecBase{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trade_id")
	private Trade trade;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_participant_id")
	private TradeParticipant fromParticipant;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_participant_id")
	private TradeParticipant toParticipant;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_collectible_id")
	private UserCollectible userCollectible;
	private int quantity;
}
