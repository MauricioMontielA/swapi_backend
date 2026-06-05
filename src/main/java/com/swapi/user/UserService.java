package com.swapi.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.swapi.user.dto.UserDetailsDtoResponse;
import com.swapi.user.dto.UserDtoResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepo;
	private final UserMapper userMapper;
	
	public UserDetailsDtoResponse getDetailsById(Long userId) {
		Optional<User> optUser = userRepo.findById(userId);
		if (!optUser.isPresent()) {
			return null;
		}
		User user = optUser.get();
		UserDetailsDtoResponse dtoResponse = userMapper.toResponseDetails(user);
		int countTrades = userRepo.getCountTradesByUser(userId);
		dtoResponse.setSwapsQuantity(countTrades);
		int countCollection = userRepo.getCountCollectionsByUser(userId);
		dtoResponse.setCollectionQuantity(countCollection);
		
		return dtoResponse;
	}
	
	public List<UserDtoResponse> getAll(){
		return userMapper.toResponseList(userRepo.findAll());
	}
}
