package com.swapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sp_tradeItem")
public class TradeItem extends RecBase{
	@ManyToOne
	@JoinColumn(name = "trade_id")
	private Trade trade;
	@ManyToOne
	@JoinColumn(name = "from_participant_id")
	private TradeParticipant fromParticipant;
	@ManyToOne
	@JoinColumn(name = "to_participant_id")
	private TradeParticipant toParticipant;
	@ManyToOne
	@JoinColumn(name = "user_collectible_id")
	private UserCollectible userCollectible;
	private int quantity;
}
