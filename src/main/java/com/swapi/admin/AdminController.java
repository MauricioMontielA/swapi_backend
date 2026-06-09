package com.swapi.admin;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.collectibleItem.CollectibleItemDtoResponse;
import com.swapi.collectibleItem.CollectibleItemMapper;
import com.swapi.collectibleItem.CollectibleItemRepository;
import com.swapi.collectibleItem.CollectibleItemService;
import com.swapi.collection.Collection;
import com.swapi.collection.CollectionMapper;
import com.swapi.collection.CollectionRepository;
import com.swapi.collection.CollectionService;
import com.swapi.collection.dto.CollectionInfoDtoResponse;
import com.swapi.user.UserMapper;
import com.swapi.user.UserRepository;
import com.swapi.user.UserService;
import com.swapi.user.dto.UserDtoResponse;

import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private final CollectionService collectionService;
	private final CollectibleItemService collectibleItemService;
	private final UserService userService;

	@PostMapping("/collection")
	public Collection createCollection(@RequestBody Collection collection) {
		return collectionService.create(collection);
	}
	
	@GetMapping("/collection")
	public List<CollectionInfoDtoResponse> getAllCollections() {
		return collectionService.getAllCollections();
	}
	
	@PostMapping("/uploadItems")
	public List<CollectibleItemDtoResponse> uploadCsv(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("collectionCode") String collectionCode
	) throws Exception {
		return collectibleItemService.uploadItemsForCollectionByCsv(file, collectionCode);
	}
	
	@GetMapping("/user")
	public List<UserDtoResponse> getAllUsers() {
		return userService.getAll();
	}
}
