package com.swapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.swapi.dto.TradeDtoResponse;
import com.swapi.model.Trade;
import com.swapi.model.TradeItem;
import com.swapi.model.TradeParticipant;

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
}
