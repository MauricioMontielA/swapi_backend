package com.swapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sp_trade")
public class Trade extends RecBase{
	private String status;
	
	@OneToMany(mappedBy = "trade")
	List<TradeItem> tradeItems = new ArrayList<>();
	
	@OneToMany(mappedBy = "trade")
	List<TradeParticipant> tradeParticipants = new ArrayList<>();
}
