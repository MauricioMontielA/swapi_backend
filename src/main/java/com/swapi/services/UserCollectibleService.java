package com.swapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.swapi.dto.CollectibleItemDtoResponse;
import com.swapi.dto.UserCollectibleDtoRequest;
import com.swapi.dto.UserCollectibleDtoResponse;
import com.swapi.filter.UserCollectibleFilter;
import com.swapi.filter.UserCollectibleSpecs;
import com.swapi.mapper.CollectibleItemMapper;
import com.swapi.mapper.UserCollectibleMapper;
import com.swapi.model.CollectibleItem;
import com.swapi.model.User;
import com.swapi.model.UserCollectible;
import com.swapi.repositories.CollectibleItemRepository;
import com.swapi.repositories.UserCollectibleRepository;
import com.swapi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCollectibleService {
	private final UserRepository userRepo;
	private final CollectibleItemRepository collItemRepo;
	private final UserCollectibleRepository userCollRepo;
	private final UserCollectibleMapper userColMapper;
	private final CollectibleItemMapper colItemMapper;

	
	public UserCollectibleDtoResponse  createUserCollectible(User user, UserCollectibleDtoRequest dtoRequest) {
		Optional<CollectibleItem> item = collItemRepo.findById(dtoRequest.getCollectibleItemId());


		UserCollectible newUserItem = userColMapper.toEntity(dtoRequest, user, item.get());
		userCollRepo.save(newUserItem);
		return userColMapper.toResponse(newUserItem);
	}
	
	public List<CollectibleItemDtoResponse> getCollectiblesByUser(Long userId) {
		List<UserCollectible> items = userCollRepo.findByUserIdWithItems(userId);
		return items.stream()
				.map(userItem -> userItem.getCollectibleItem())
				.map(item ->{
					CollectibleItemDtoResponse dtoResponse = colItemMapper.toResponse(item);
					return dtoResponse;
				})
				.toList();
	}
	
	public List<CollectibleItemDtoResponse> search(Long userId, UserCollectibleFilter filter) {
		filter.setUserId(userId);
		return userCollRepo
	            .findAll(UserCollectibleSpecs.withFilter(filter))
	            .stream()
	            .map(UserCollectible::getCollectibleItem)
	            .map(colItemMapper::toResponse)
	            .toList();
	}

}
