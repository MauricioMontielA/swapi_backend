package com.swapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.dto.CollectibleItemDtoResponse;
import com.swapi.dto.CollectionDtoResponse;
import com.swapi.mapper.CollectibleItemMapper;
import com.swapi.model.CollectibleItem;
import com.swapi.repositories.CollectibleItemRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collectible-item")
@RequiredArgsConstructor
public class CollectibleItemController {
	private final CollectibleItemRepository collectibleItemRepo;
	private final CollectibleItemMapper mapper;


	@GetMapping()
	public List<CollectibleItemDtoResponse> getCollections(@RequestParam(required = false) Long collectionId) {
		List<CollectibleItem> items = null;
		if(collectionId != null) {
			items = collectibleItemRepo.findByCollectionId(collectionId);
		}else {
			items = collectibleItemRepo.findAll();
		}
		
		return items.stream()
				.map(item ->{
					CollectibleItemDtoResponse itemDto = mapper.toResponse(item);
					
					itemDto.setLinks(
						Map.of(
							"self", "/collectible-item/" + item.getId()
						)
					);
					return itemDto;
				})
				.toList();
	}
	


}
