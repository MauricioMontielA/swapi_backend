package com.swapi.tradeParticipant;

import lombok.Data;

@Data
public class TradeParticipantDtoResponse {
	private Long idUser;
	private Long idParticipant;
	private String username;
	private String profileImageUrl; 
}
