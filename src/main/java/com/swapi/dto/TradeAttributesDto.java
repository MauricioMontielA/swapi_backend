package com.swapi.dto;

import lombok.Data;

@Data
public class TradeAttributesDto {
	private Long fromParticipant;
	private Long toParticipant;
	private Long item;
	private int quantity;
}
