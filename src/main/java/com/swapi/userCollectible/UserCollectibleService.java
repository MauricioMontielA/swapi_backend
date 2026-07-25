package com.swapi.userCollectible;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.collectibleItem.CollectibleItemMapper;
import com.swapi.collectibleItem.CollectibleItemRepository;
import com.swapi.collectibleItem.dto.CollectibleItemBasicDto;
import com.swapi.collectibleItem.dto.CollectibleItemDtoResponse;
import com.swapi.trade.dto.TradeCandidateItemsDto;
import com.swapi.user.User;
import com.swapi.user.UserRepository;
import com.swapi.userCollectible.dto.UserCollectibleMatchRepoDto;
import com.swapi.userCollectible.dto.UserCollectibleMatchResponseDto;
import com.swapi.userCollectible.dto.UserColMatchInfoRequestDto;
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

	
	public UserCollectibleMatchResponseDto  createUserCollectible(User user, UserCollectibleDtoRequest dtoRequest) {
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
			return new UserCollectibleMatchResponseDto();
		}
		
		List<UserCollectibleMatchRepoDto> matches = getSmartSwapsOrderByBestChoice(user.getId(), 
				dtoRequest.getCollectionId(), 
				repeatedItems.stream().map(UserCollectible::getId).toList());
		
		UserCollectibleMatchResponseDto response = new UserCollectibleMatchResponseDto();
		response.setMatchInfo(matches);
		response.setMyItemsToOffer(repeatedItems.stream()
				.map(repeatedItem -> repeatedItem.getCollectibleItem().getId())
				.toList());

		return response;
	}
	
	public void getItemsForTrade(Long userToTrade, List<Long> idsForTrade) {
		
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
	
	public Page<UserCollectibleCollectionViewDto> getCollectibleItemsByUserCollection(Long collectionId, Long userId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		return userCollRepo.findOwnershipByUserAndCollection(userId, collectionId, pageable);
	}
	
	public boolean isOwner(UserCollectible item, long userId) {
		return item.getUser().getId().equals(userId);
	}
	
	public List<UserCollectibleMatchRepoDto> getSmartSwapsOrderByBestChoice(Long userId, Long collectionId) {
		return getSmartSwapsOrderByBestChoice(userId, collectionId, null);
	}
	
	public List<UserCollectibleMatchRepoDto> getSmartSwapsOrderByBestChoice(Long userId, Long collectionId, List<Long> offeredItemIds) {
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
