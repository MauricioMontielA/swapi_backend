package com.swapi.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.swapi.dto.CollectionDtoResponse;
import com.swapi.mapper.CollectionMapper;
import com.swapi.model.Collection;
import com.swapi.repositories.CollectionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionService {
	private final CollectionRepository collectionRepo;
	private final CollectionMapper collectionMapper;
	
	public List<CollectionDtoResponse> getAllCollections() {
	    List<Collection> collections = collectionRepo.findAll();
	    
	    return collections.stream()
	            .map(collection -> {
	            	CollectionDtoResponse dto = collectionMapper.toResponse(collection);
	                dto.setLinks(
	                	Map.of(
	                		"self", "/collection/" + collection.getId(),
	                        "collectibleItems","/collectible-item?collectionId=" + collection.getId()
	                    )
	                );
	                return dto;
	            })
	            .toList();

	}
	
	public CollectionDtoResponse getCollectionById(Long id) {
		Collection collection = collectionRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Item not found"));
		CollectionDtoResponse dto = collectionMapper.toResponse(collection);
		dto.setLinks(
			Map.of(
				"self", "/collection/" + collection.getId()
			)
		);
		
	    return dto;

	}
	
}
