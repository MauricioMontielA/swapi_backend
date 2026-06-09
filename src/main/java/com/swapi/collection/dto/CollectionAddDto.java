package com.swapi.collection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionAddDto {
	private long id;
	private String title;
	private String description;
	private String imageUrl;
}
