package com.swapi.services;

import org.springframework.stereotype.Service;

import com.swapi.model.UserCollectible;
import com.swapi.repositories.CollectibleItemRepository;
import com.swapi.repositories.UserCollectibleRepository;
import com.swapi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCollectibleService {
	UserRepository userRepo;
	CollectibleItemRepository collItemRepo;
	UserCollectibleRepository userCollRepo;
	
	public UserCollectible createUserCollectible(Long userId, Long itemId) {
		UserCollectible newUserItem = new UserCollectible();
		return newUserItem;
	}

}
