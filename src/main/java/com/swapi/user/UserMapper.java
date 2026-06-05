package com.swapi.user;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.swapi.user.dto.UserDetailsDtoResponse;
import com.swapi.user.dto.UserDtoResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDtoResponse toResponse(User collection);

	List<UserDtoResponse> toResponseList(List<User> collections);
	
    @Mapping(target = "profileCreationDate", source = "createdAt")
	UserDetailsDtoResponse toResponseDetails(User collection);
}
