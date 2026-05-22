package com.swapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.swapi.dto.UserDtoResponse;
import com.swapi.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDtoResponse toResponse(User collection);

	List<UserDtoResponse> toResponseList(List<User> collections);
}
