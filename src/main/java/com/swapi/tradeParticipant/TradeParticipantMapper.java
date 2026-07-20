package com.swapi.tradeParticipant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.swapi.tradeParticipant.dto.TradeParticipantGeneralViewDto;

@Mapper(componentModel = "spring")
public interface TradeParticipantMapper {

    @Mapping(target = "idUser", source = "user.id")
    @Mapping(target = "idParticipant", source = "id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "profileImageUrl", source = "user.profileImageUrl")
    TradeParticipantDtoResponse toResponse(TradeParticipant tradeParticipant);
    
    @Mapping(target = "name", source = "user.username")
    @Mapping(target = "profileImageUrl", source = "user.profileImageUrl")
    @Mapping(target = "reputation", source = "user.rating")
    TradeParticipantGeneralViewDto toGeneralViewDto(TradeParticipant tradeParticipant);

}
