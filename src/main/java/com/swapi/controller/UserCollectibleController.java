package com.swapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.dto.CollectibleItemDtoResponse;
import com.swapi.dto.UserCollectibleDtoRequest;
import com.swapi.dto.UserCollectibleDtoResponse;
import com.swapi.filter.UserCollectibleFilter;
import com.swapi.model.UserCollectible;
import com.swapi.services.CollectionService;
import com.swapi.services.UserCollectibleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-collectible")
@RequiredArgsConstructor
public class UserCollectibleController {
	private final UserCollectibleService userColService;
	
	@PostMapping
	public UserCollectibleDtoResponse createUserColl(@RequestBody UserCollectibleDtoRequest userCollDtoReq) {
		return userColService.createUserCollectible(userCollDtoReq);
	}
	
//	@GetMapping
//	public List<CollectibleItemDtoResponse> getCollectibleItemsByUser(@RequestParam(required = false) Long userId) {
//		return userColService.getCollectiblesByUser(userId);
//	}
	
	@GetMapping
	public List<CollectibleItemDtoResponse> getCollectibleItemsByUser(UserCollectibleFilter filter) {
	    return userColService.search(filter);
	}
}
