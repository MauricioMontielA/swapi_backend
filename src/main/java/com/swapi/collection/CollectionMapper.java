package com.swapi.collection;

import java.util.List;

import org.mapstruct.Mapper;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.collectibleItem.CollectibleItemDtoResponse;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
	CollectionDtoResponse toResponse(Collection collection);

	List<CollectionDtoResponse> toResponseList(List<Collection> collections);
}
