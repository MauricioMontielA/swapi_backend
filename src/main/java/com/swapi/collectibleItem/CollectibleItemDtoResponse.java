package com.swapi.collectibleItem;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectibleItemDtoResponse {
	private int id;
	private String number;
	private String code;
	private String name;
	private String imageUrl;
	private String rarity;

	private Integer collectionId;
	private String collectionName;
	
}
