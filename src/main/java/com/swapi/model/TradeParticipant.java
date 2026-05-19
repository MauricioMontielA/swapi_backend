package com.swapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sp_tradeParticipant")
public class TradeParticipant extends RecBase{
	@ManyToOne
	@JoinColumn(name = "trade_id")
	private Trade trade;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String status;
	
	@OneToMany(mappedBy = "fromParticipant")
	List<TradeItem> tradeItemsFrom = new ArrayList<>();
	
	@OneToMany(mappedBy = "toParticipant")
	List<TradeItem> tradeItemsTo = new ArrayList<>();
}
