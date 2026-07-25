package com.swapi.userCollectible;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.CustomUserPrincipal;
import com.swapi.collectibleItem.dto.CollectibleItemDtoResponse;
import com.swapi.collection.CollectionService;
import com.swapi.userCollectible.dto.UserCollectibleMatchRepoDto;
import com.swapi.userCollectible.dto.UserCollectibleMatchResponseDto;
import com.swapi.userCollectible.dto.UserCollectibleCollectionViewDto;
import com.swapi.userCollectible.dto.UserCollectibleDtoRequest;
import com.swapi.userCollectible.dto.UserCollectibleDtoResponse;
import com.swapi.userCollectible.filter.UserCollectibleFilter;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-collectible")
@RequiredArgsConstructor
public class UserCollectibleController {
	private final UserCollectibleService userColService;
	
		@PostMapping
	    public UserCollectibleMatchResponseDto create(
	            @AuthenticationPrincipal CustomUserPrincipal user,
	            @RequestBody UserCollectibleDtoRequest request
	    ) {
        return userColService.createUserCollectible(user.getUser(), request);
    }
    
    @GetMapping()
    public Page<UserCollectibleCollectionViewDto> getMine(
            @AuthenticationPrincipal CustomUserPrincipal user,
            @RequestParam Long collectionId,
            @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "30") int size
    ) {
        return userColService.getCollectibleItemsByUserCollection(user.getId(), collectionId, page, size);
    }
}
