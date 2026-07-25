package com.swapi.userCollectible.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectibleCollectionViewDto {
	private Number id;
	private String number;
	private Number owned;
	private String imageUrl;
}
