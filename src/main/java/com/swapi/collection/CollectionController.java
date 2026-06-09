package com.swapi.collection;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.CustomUserPrincipal;
import com.swapi.collectibleItem.CollectibleItemRepository;
import com.swapi.collection.dto.CollectionAddDto;
import com.swapi.collection.dto.CollectionInfoDtoResponse;
import com.swapi.collection.dto.CollectionAddProgressDto;
import com.swapi.collection.dto.CollectionAddResponseDto;
import com.swapi.collection.dto.CollectionDetailedItemsDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {
	private final CollectionService collectionService;
	
	@GetMapping("/all")
	public List<CollectionInfoDtoResponse> getAllCollections() {
		return collectionService.getAllCollections();
	}
	
	@GetMapping("/{id}")
	public CollectionInfoDtoResponse getCollection(@PathVariable Long id) {
		return collectionService.getCollectionById(id);
	}
	
	@GetMapping("/add")
	public CollectionAddResponseDto getCollectionProgress(@AuthenticationPrincipal CustomUserPrincipal user) {
		return collectionService.getAddResponse(user.getId());
	}
	
	@GetMapping()
	public List<CollectionDetailedItemsDto> getCollectionItemsDetailed(@AuthenticationPrincipal CustomUserPrincipal user) {
		return collectionService.getCollectionItemsDetailedById(user.getId());
	}

}
