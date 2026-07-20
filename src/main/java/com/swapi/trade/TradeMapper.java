package com.swapi.trade;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.swapi.model.auxiliar.TradeParticipantStatus;
import com.swapi.model.auxiliar.TradeStatus;
import com.swapi.trade.dto.TradeDtoResponse;
import com.swapi.trade.dto.TradeGeneralViewResDto;
import com.swapi.tradeItem.TradeItem;
import com.swapi.tradeItem.dto.TradeItemoGeneralViewDto;
import com.swapi.tradeParticipant.TradeParticipant;
import com.swapi.tradeParticipant.TradeParticipantMapper;
import com.swapi.tradeParticipant.dto.TradeParticipantGeneralViewDto;
import com.swapi.userCollectible.UserCollectibleMapper;
import com.swapi.util.TimeUtils;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    TradeParticipantMapper tradeParticipantMapper = Mappers.getMapper(TradeParticipantMapper.class);
    UserCollectibleMapper userCollectibleMapper = Mappers.getMapper(UserCollectibleMapper.class);

    default TradeDtoResponse toResponse(Trade trade, Long currentUserId) {

        TradeDtoResponse dto = new TradeDtoResponse();

        dto.setCreatedAt(trade.getCreatedAt());
        dto.setStatus(trade.getStatus());

        TradeParticipant otherParticipant = trade.getTradeParticipants()
                .stream()
                .filter(tp -> !tp.getUser().getId().equals(currentUserId))
                .findFirst()
                .orElse(null);

        dto.setOtherParticipant(
                tradeParticipantMapper.toResponse(otherParticipant)
        );

        for (TradeItem tradeItem : trade.getTradeItems()) {

            if (tradeItem.getUserCollectible().getUser().getId().equals(currentUserId)) {

                dto.setMyItem(
                        userCollectibleMapper.toTradeResponse(
                                tradeItem.getUserCollectible()
                        )
                );

            } else {

                dto.setOtherItem(
                        userCollectibleMapper.toTradeResponse(
                                tradeItem.getUserCollectible()
                        )
                );
            }
        }

        return dto;
    }
    
    default TradeGeneralViewResDto toGeneralViewResponse(Trade trade, Long currentUserId, long countTrades) {

    	TradeGeneralViewResDto tradeDto = new TradeGeneralViewResDto();
    	tradeDto.setId(trade.getId());
    	tradeDto.setStatus(trade.getStatus());
    	
    	TradeParticipant otherParticipant = trade.getTradeParticipants()
                .stream()
                .filter(tp -> !tp.getUser().getId().equals(currentUserId))
                .findFirst()
                .orElse(null);
    	    	
    	if(trade.getStatus().equals(TradeStatus.OPEN) ) {
        	tradeDto.setDirection(otherParticipant.getStatus().equals(TradeParticipantStatus.PROPOSED) ? "incoming" : "outgoing");
    	}
    	tradeDto.setTimeQuantity(TimeUtils.timeAgo(trade.getCreatedAt()));
    	tradeDto.setTimeUom(TimeUtils.timeAgoUOM(trade.getCreatedAt()));
    	tradeDto.setCollection(trade.getTradeItems()
    			.stream()
    			.findFirst()
    			.get().getUserCollectible().getCollectibleItem().getCollection().getName());
    	tradeDto.setNote("");
    	
    	TradeParticipantGeneralViewDto trader = tradeParticipantMapper.toGeneralViewDto(otherParticipant);
    	trader.setTrades(countTrades);
        tradeDto.setTrader(trader);
        
        List<TradeItemoGeneralViewDto> offeredItem = new ArrayList<>();
        List<TradeItemoGeneralViewDto> requestedItems = new ArrayList<>();
        for (TradeItem tradeItem : trade.getTradeItems()) {
            if (tradeItem.getUserCollectible().getUser().getId().equals(currentUserId)) {
            	requestedItems.add(userCollectibleMapper.toItemGeneralView(tradeItem.getUserCollectible()));
            } else {
            	offeredItem.add(userCollectibleMapper.toItemGeneralView(tradeItem.getUserCollectible()));
            }
        }
        
        tradeDto.setOfferedItems(offeredItem);
        tradeDto.setRequestedItems(requestedItems);

        return tradeDto;
    }
}
