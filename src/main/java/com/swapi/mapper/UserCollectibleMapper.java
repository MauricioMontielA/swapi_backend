package com.swapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.swapi.dto.UserCollectibleDtoRequest;
import com.swapi.dto.UserCollectibleDtoResponse;
import com.swapi.model.CollectibleItem;
import com.swapi.model.User;
import com.swapi.model.UserCollectible;

@Mapper(componentModel = "spring")
public interface UserCollectibleMapper {
	
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "collectibleItem", source = "collectibleItem")
    UserCollectible toEntity(
            UserCollectibleDtoRequest dto,
            User user,
            CollectibleItem collectibleItem
    );
    
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "itemCode", source = "collectibleItem.code")
    UserCollectibleDtoResponse toResponse(UserCollectible entity);
}
