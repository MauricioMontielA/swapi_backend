package com.swapi.userCollectible;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.collectibleItem.CollectibleItemDtoResponse;
import com.swapi.collectibleItem.CollectibleItemMapper;
import com.swapi.collectibleItem.CollectibleItemRepository;
import com.swapi.trade.dto.TradeMatchDto;
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

	
	public List<TradeMatchDto>  createUserCollectible(User user, UserCollectibleDtoRequest dtoRequest) {
		List<CollectibleItem> addedItems = collItemRepo.findByIdIn(dtoRequest.getCollectibleItemIds());
		List<UserCollectible> userItems = userCollRepo
				.findByUserIdAndCollectibleItemIdIn(user.getId(), dtoRequest.getCollectibleItemIds());
		List<CollectibleItem> collectibleItemsUser = userItems.stream().map(UserCollectible::getCollectibleItem).toList();
		
		addedItems.stream()
				.filter(item -> !collectibleItemsUser.contains(item))
				.map(collectibleItem -> userColMapper.toEntity(dtoRequest, user, collectibleItem))
				.forEach(userItem -> {
					userItem.setQuantity(1);
					userCollRepo.save(userItem);
				});
		
		List<UserCollectible> repeatedItems = userItems.stream()
			    .map(userItem -> {
			        userItem.setQuantity(userItem.getQuantity() + 1);
			        return userCollRepo.save(userItem);
			    })
			    .filter(userItem -> userItem.getQuantity() > 1)
			    .toList();
		
		if (repeatedItems == null || repeatedItems.isEmpty()) {
			return new ArrayList<>();
		}
		
		return getSmartSwapsOrderByBestChoice(user.getId(), 
				repeatedItems.get(0).getCollectibleItem().getCollection().getId(), 
				repeatedItems.stream().map(UserCollectible::getId).toList());
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
	
	public List<TradeMatchDto> getSmartSwapsOrderByBestChoice(Long userId, Long collectionId) {
		return getSmartSwapsOrderByBestChoice(userId, collectionId, null);
	}
	
	public List<TradeMatchDto> getSmartSwapsOrderByBestChoice(Long userId, Long collectionId, List<Long> offeredItemIds) {
		if (userId == null || collectionId == null) {
			return null;
		}
		if (offeredItemIds == null || offeredItemIds.isEmpty()) {
			return userCollRepo.findBestMatchesForSwap(userId, collectionId);
		}else {
			return userCollRepo.findBestMatchesForSwapWithItemsIds(userId, collectionId, offeredItemIds, offeredItemIds.size());
		}
	}

}
