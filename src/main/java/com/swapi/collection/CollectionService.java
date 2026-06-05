package com.swapi.collection;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.swapi.exception.RecordNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionService {
	private final CollectionRepository collectionRepo;
	private final CollectionMapper collectionMapper;
	
	public Collection create(Collection collection) {
		return collectionRepo.save(collection);
	}
	
	public List<CollectionDtoResponse> getAllCollections() {
	    List<Collection> collections = collectionRepo.findAll();
	    
	    return collections.stream()
	            .map(collection -> collectionMapper.toResponse(collection))
	            .toList();

	}
	
	public CollectionDtoResponse getCollectionById(Long id) {
		Collection collection = collectionRepo.findById(id)
	            .orElseThrow(() -> new RecordNotFoundException("Collection not found"));
		CollectionDtoResponse dto = collectionMapper.toResponse(collection);
		
	    return dto;

	}
	
}
