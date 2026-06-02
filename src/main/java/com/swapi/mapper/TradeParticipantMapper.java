package com.swapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.swapi.dto.TradeParticipantDtoResponse;
import com.swapi.model.TradeParticipant;

@Mapper(componentModel = "spring")
public interface TradeParticipantMapper {

    @Mapping(target = "idUser", source = "user.id")
    @Mapping(target = "idParticipant", source = "id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "profileImageUrl", source = "user.profileImageUrl")
    TradeParticipantDtoResponse toResponse(TradeParticipant tradeParticipant);

}
