package com.swapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.swapi.dto.CollectibleItemDtoResponse;
import com.swapi.dto.CollectionDtoResponse;
import com.swapi.model.CollectibleItem;
import com.swapi.model.Collection;

@Mapper(componentModel = "spring")
public interface CollectionMapper {
	CollectionDtoResponse toResponse(Collection collection);

	List<CollectionDtoResponse> toResponseList(List<Collection> collections);
}
