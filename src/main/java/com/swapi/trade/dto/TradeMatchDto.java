package com.swapi.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeMatchDto {
	private Integer userId;
	private String username;
	private Integer myOfferItemsCount;
	private Integer theyOfferItemsCount;
}
