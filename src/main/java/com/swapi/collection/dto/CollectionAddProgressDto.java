package com.swapi.collection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionAddProgressDto extends CollectionAddDto{
	private long id;
	private String name;
	private String description;
	private String imageUrl;
	private double progress;

}
