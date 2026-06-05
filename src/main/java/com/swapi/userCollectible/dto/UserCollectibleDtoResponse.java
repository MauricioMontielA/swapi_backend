package com.swapi.userCollectible.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCollectibleDtoResponse {
	private String username;
	private String itemCode;
	private int quantity;
	private String notes;
	private boolean isForTrade;
	private boolean isForSale;
}
