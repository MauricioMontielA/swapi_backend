package com.swapi.collection.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionInfoDtoResponse {
	private String name;
	private String code;
	private String type;
	
}
