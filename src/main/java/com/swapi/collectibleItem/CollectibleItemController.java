package com.swapi.collectibleItem;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.collection.dto.CollectionInfoDtoResponse;

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
}
