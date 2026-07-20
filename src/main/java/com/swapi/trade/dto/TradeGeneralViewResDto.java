package com.swapi.trade.dto;

import java.util.List;

import com.swapi.model.auxiliar.TradeStatus;
import com.swapi.tradeItem.dto.TradeItemoGeneralViewDto;
import com.swapi.tradeParticipant.dto.TradeParticipantGeneralViewDto;

import lombok.Data;

@Data
public class TradeGeneralViewResDto {
	private long id;
	private TradeStatus status;
	private String direction;
	private Long timeQuantity;
	private String timeUom;
	private String collection;
	private String note;
	private TradeParticipantGeneralViewDto trader;
	private List<TradeItemoGeneralViewDto> offeredItems;
	private List<TradeItemoGeneralViewDto> requestedItems;
}
