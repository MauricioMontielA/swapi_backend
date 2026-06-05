package com.swapi.collection;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.collectibleItem.CollectibleItemRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/collection")
@RequiredArgsConstructor
public class CollectionController {
	private final CollectionService collectionService;
	
	@GetMapping()
	public List<CollectionDtoResponse> getAllCollections() {
		return collectionService.getAllCollections();
	}
	
	@GetMapping("/{id}")
	public CollectionDtoResponse getCollection(@PathVariable Long id) {
		return collectionService.getCollectionById(id);
	}

}
