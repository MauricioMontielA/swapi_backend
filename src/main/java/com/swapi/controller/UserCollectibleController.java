package com.swapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.model.UserCollectible;
import com.swapi.services.CollectionService;
import com.swapi.services.UserCollectibleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-collectible")
@RequiredArgsConstructor
public class UserCollectibleController {
	private UserCollectibleService userColService;
	
	@PostMapping
	private UserCollectible createUserColl() {
		return userColService.createUserCollectible(null, null);
	}
}
