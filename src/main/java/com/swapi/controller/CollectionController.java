package com.swapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.dto.CollectionDtoResponse;
import com.swapi.mapper.CollectionMapper;
import com.swapi.model.Collection;
import com.swapi.repositories.CollectibleItemRepository;
import com.swapi.repositories.CollectionRepository;
import com.swapi.services.CollectionService;

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
