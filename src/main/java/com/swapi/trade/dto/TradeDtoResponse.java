package com.swapi.trade.dto;

import java.time.LocalDateTime;

import com.swapi.model.auxiliar.TradeStatus;
import com.swapi.tradeParticipant.TradeParticipantDtoResponse;
import com.swapi.userCollectible.dto.UserCollectibleBasicInfoDto;

import lombok.Data;

@Data
public class TradeDtoResponse {
	private TradeParticipantDtoResponse otherParticipant;
	private UserCollectibleBasicInfoDto otherItem;
	private UserCollectibleBasicInfoDto myItem;
	private LocalDateTime createdAt;
	private TradeStatus status;
}
