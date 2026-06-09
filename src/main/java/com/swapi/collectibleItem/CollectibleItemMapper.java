package com.swapi.collectibleItem;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CollectibleItemMapper {
	@Mapping(source = "collection.id", target = "collectionId")
    @Mapping(source = "collection.name", target = "collectionName")
	CollectibleItemDtoResponse toResponse(CollectibleItem item);

	List<CollectibleItemDtoResponse> toResponseList(List<CollectibleItem> items);
	
	CollectibleItemBasicDto toBasicResponse(CollectibleItem item);
}
