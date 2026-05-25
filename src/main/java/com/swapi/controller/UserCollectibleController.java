package com.swapi.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.CustomUserPrincipal;
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
    public UserCollectibleDtoResponse create(
            @AuthenticationPrincipal CustomUserPrincipal user,
            @RequestBody UserCollectibleDtoRequest request
    ) {
        return userColService.createUserCollectible(user.getUser(), request);
    }

    @GetMapping()
    public List<CollectibleItemDtoResponse> getMine(
            @AuthenticationPrincipal CustomUserPrincipal user,
            UserCollectibleFilter filter
    ) {
        return userColService.search(user.getId(), filter);
    }
}
