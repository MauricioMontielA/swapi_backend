package com.swapi.userCollectible.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectibleCollectionViewDto {
	private Integer id;
	private String number;
	private Integer owned;
	private String imageUrl;
}
