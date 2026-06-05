package com.swapi.collectibleItem;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.collection.CollectionDtoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collectible-item")
@RequiredArgsConstructor
public class CollectibleItemController {
	private final CollectibleItemService collectibleItemService;

	@GetMapping()
	public List<CollectibleItemDtoResponse> getCollections(@RequestParam(required = false) Long collectionId) {
		return collectibleItemService.getCollectibleItemByCollectionOpt(collectionId);
	}
}
