package com.swapi.trade.dto;

import java.util.List;

import com.swapi.userCollectible.dto.UserCollectibleBasicInfoDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeCandidateItemsDto {
	List<UserCollectibleBasicInfoDto> desiredItems;
	List<UserCollectibleBasicInfoDto> offeredItems;

}
