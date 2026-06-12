package com.swapi.userCollectible.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectibleMatchRepoDto {
	private Integer userId;
	private String username;
	private Integer myOfferItemsCount;
	private Integer theyOfferItemsCount;
}
