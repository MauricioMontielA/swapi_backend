package com.swapi.dto;

import java.time.LocalDateTime;

import com.swapi.model.auxiliar.TradeStatus;

import lombok.Data;

@Data
public class TradeDtoResponse {
	private TradeParticipantDtoResponse otherParticipant;
	private UserCollTradeDtoResponse otherItem;
	private UserCollTradeDtoResponse myItem;
	private LocalDateTime createdAt;
	private TradeStatus status;
}
