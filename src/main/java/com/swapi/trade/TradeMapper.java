package com.swapi.trade;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.swapi.trade.dto.TradeDtoResponse;
import com.swapi.tradeItem.TradeItem;
import com.swapi.tradeParticipant.TradeParticipant;
import com.swapi.tradeParticipant.TradeParticipantMapper;
import com.swapi.userCollectible.UserCollectibleMapper;

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
