package com.swapi.userCollectible;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.collectibleItem.dto.CollectibleItemDtoResponse;
import com.swapi.user.User;
import com.swapi.userCollectible.dto.UserCollectibleBasicInfoDto;
import com.swapi.userCollectible.dto.UserCollectibleDtoRequest;
import com.swapi.userCollectible.dto.UserCollectibleDtoResponse;

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
    
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "itemCode", source = "collectibleItem.code")
    List<UserCollectibleDtoResponse> toResponseList(List<UserCollectible> userItems);
    
    @Mapping(target = "collectibleItemNumber", source = "id")
    @Mapping(target = "collectibleItemName", source = "collectibleItem.name")
    @Mapping(target = "collectibleItemImageUrl", source = "collectibleItem.imageUrl")
    UserCollectibleBasicInfoDto toTradeResponse(UserCollectible entity);
}
