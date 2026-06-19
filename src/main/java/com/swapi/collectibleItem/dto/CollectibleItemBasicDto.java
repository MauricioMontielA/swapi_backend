package com.swapi.collectibleItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectibleItemBasicDto {
	private long id;
	private String number;
	private String name;
	private String imageUrl;
}
