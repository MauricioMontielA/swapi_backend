package com.swapi.collection;

import java.util.List;

import org.mapstruct.Mapper;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.collectibleItem.CollectibleItemDtoResponse;
import com.swapi.collection.dto.CollectionInfoDtoResponse;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
	CollectionInfoDtoResponse toResponse(Collection collection);

	List<CollectionInfoDtoResponse> toResponseList(List<Collection> collections);
}
