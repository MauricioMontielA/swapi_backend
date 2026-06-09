package com.swapi.userCollectible;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.collectibleItem.CollectibleItemDtoResponse;
import com.swapi.collectibleItem.CollectibleItemMapper;
import com.swapi.collectibleItem.CollectibleItemRepository;
import com.swapi.user.User;
import com.swapi.user.UserRepository;
import com.swapi.userCollectible.dto.UserCollectibleCollectionViewDto;
import com.swapi.userCollectible.dto.UserCollectibleDtoRequest;
import com.swapi.userCollectible.dto.UserCollectibleDtoResponse;
import com.swapi.userCollectible.filter.UserCollectibleFilter;
import com.swapi.userCollectible.filter.UserCollectibleSpecs;

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
	
	public List<UserCollectible> getUserCollectibleByIdIn(List<Long> ids){
		return userCollRepo.findByIdIn(ids);
	}
	
	public List<UserCollectibleCollectionViewDto> getCollectibleItemsByUserCollection(Long userId, Long collectionId){
		return userCollRepo.findOwnershipByUserAndCollection(userId, collectionId);
	}
	
	public boolean isOwner(UserCollectible item, long userId) {
		return item.getUser().getId().equals(userId);
	}

}
