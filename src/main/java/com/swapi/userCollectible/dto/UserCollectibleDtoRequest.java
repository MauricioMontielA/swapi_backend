package com.swapi.userCollectible.dto;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCollectibleDtoRequest {
	private long collectibleItemId;
	private int quantity;
	private String notes;
	private boolean isForTrade;
	private boolean isForSale;
}
