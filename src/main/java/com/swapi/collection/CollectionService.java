package com.swapi.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.swapi.collection.dto.CollectionAddDto;
import com.swapi.collection.dto.CollectionInfoDtoResponse;
import com.swapi.collection.dto.CollectionAddProgressDto;
import com.swapi.collection.dto.CollectionAddResponseDto;
import com.swapi.collection.dto.CollectionDetailedItemsDto;
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
	
	public List<CollectionInfoDtoResponse> getAllCollections() {
	    List<Collection> collections = collectionRepo.findAll();
	    
	    return collections.stream()
	            .map(collection -> collectionMapper.toResponse(collection))
	            .toList();

	}
	
	public CollectionInfoDtoResponse getCollectionById(Long collectionId) {
		Collection collection = collectionRepo.findById(collectionId)
	            .orElseThrow(() -> new RecordNotFoundException("Collection not found"));
		CollectionInfoDtoResponse dto = collectionMapper.toResponse(collection);
		
	    return dto;

	}
	
	public List<CollectionDetailedItemsDto> getCollectionItemsDetailedById(Long collectionId) {
		 return collectionRepo.findCollectionItemsDetailedByUserId(collectionId);
	}
	
	public CollectionAddResponseDto getAddResponse(Long userId){
		List<CollectionAddProgressDto> inProgressList = getInProgressCollections(userId);
		List<CollectionAddDto> recommendedList = getRecommendedCollections(userId);
		CollectionAddResponseDto response = new CollectionAddResponseDto(inProgressList, recommendedList);
		return response;
	}
	
	public List<CollectionAddProgressDto> getInProgressCollections(Long userId){
		return collectionRepo.findCollectionsInProgressByUserId(userId);
	}
	
	public List<CollectionAddDto> getRecommendedCollections(Long userId){
		return collectionRepo.findRecommendedCollectionsByUserId(userId);
	}
	
}
