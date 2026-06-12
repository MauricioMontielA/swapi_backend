package com.swapi.collectibleItem;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.CustomUserPrincipal;
import com.swapi.collection.dto.CollectionInfoDtoResponse;
import com.swapi.trade.dto.TradeDtoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collectible-item")
@RequiredArgsConstructor
public class CollectibleItemController {
	private final CollectibleItemService collectibleItemService;

	@GetMapping()
	public List<CollectibleItemBasicDto> getCollectibleItems(@RequestParam(required = false) Long collectionId) {
		return collectibleItemService.getCollectibleItemByCollectionOpt(collectionId);
	}
	
	@GetMapping("/add")
	public Page<CollectibleItemBasicDto> getCollectibleItemsPage(@RequestParam(required = true) Long collectionId,
			@RequestParam(required = false) String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "30") int size){
		return collectibleItemService.getCollectibleItemByCollectionPageable(collectionId, name, page, size);
	}
	
}
