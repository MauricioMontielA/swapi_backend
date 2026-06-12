package com.swapi.trade.dto;

import java.time.LocalDateTime;

import com.swapi.model.auxiliar.TradeStatus;
import com.swapi.tradeParticipant.TradeParticipantDtoResponse;
import com.swapi.userCollectible.dto.UserCollTradeDtoResponse;

import lombok.Data;

@Data
public class TradeDtoResponse {
	private TradeParticipantDtoResponse otherParticipant;
	private UserCollTradeDtoResponse otherItem;
	private UserCollTradeDtoResponse myItem;
	private LocalDateTime createdAt;
	private TradeStatus status;
}
