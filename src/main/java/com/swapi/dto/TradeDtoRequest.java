package com.swapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class TradeDtoRequest {
	private List<TradeAttributesDto> itemsToTrade;
}


