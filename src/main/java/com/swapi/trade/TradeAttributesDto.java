package com.swapi.trade;

import lombok.Data;

@Data
public class TradeAttributesDto {
	private Long fromUser;
	private Long toUser;
	private Long userCollectible;
	private int quantity;
}
