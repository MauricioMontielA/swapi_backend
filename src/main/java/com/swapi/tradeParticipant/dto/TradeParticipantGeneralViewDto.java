package com.swapi.tradeParticipant.dto;

import lombok.Data;

@Data
public class TradeParticipantGeneralViewDto {
	private String name;
	private String profileImageUrl;
	private double reputation;
	private double trades;
}
