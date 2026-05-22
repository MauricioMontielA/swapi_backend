package com.swapi.dto;

import com.swapi.model.CollectibleItem;
import com.swapi.model.User;

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
	private long userId;
	private long collectibleItemId;
	private int quantity;
	private String notes;
	private boolean isForTrade;
	private boolean isForSale;
}
