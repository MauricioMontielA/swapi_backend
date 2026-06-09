package com.swapi.collection.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionAddResponseDto {
	private List<CollectionAddProgressDto> collectionsInProgress;
	private List<CollectionAddDto> recommendedCollections;
}
