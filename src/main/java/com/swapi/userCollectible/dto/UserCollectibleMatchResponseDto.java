package com.swapi.userCollectible.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectibleMatchResponseDto {
	private List<UserCollectibleMatchRepoDto> matchInfo;
	private List<Long> myItemsToOffer;
}
