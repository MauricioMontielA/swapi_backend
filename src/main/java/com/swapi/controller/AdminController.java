package com.swapi.controller;

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

import com.swapi.dto.CollectionDtoResponse;
import com.swapi.dto.UserDtoResponse;
import com.swapi.mapper.CollectibleItemMapper;
import com.swapi.mapper.CollectionMapper;
import com.swapi.mapper.UserMapper;
import com.swapi.model.CollectibleItem;
import com.swapi.model.Collection;
import com.swapi.repositories.CollectibleItemRepository;
import com.swapi.repositories.CollectionRepository;
import com.swapi.repositories.UserRepository;

import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final CollectionRepository collectionRepo;
	private final CollectibleItemRepository collectibleItemRepo;
	private final CollectionMapper collectionMapper;
	private final UserRepository userRepo;
	private final UserMapper userMapper;

	
	
	@PostMapping("/collection")
	public Collection createCollection(@RequestBody Collection collection) {
		return collectionRepo.save(collection);
	}
	
	@GetMapping("/collection")
	public List<CollectionDtoResponse> getAllCollections() {
		return collectionMapper.toResponseList(collectionRepo.findAll());
	}
	
	@PostMapping("/uploadItems")
	public List<CollectibleItem> uploadCsv(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("collectionCode") String collectionCode
	) throws Exception {
		
		
		Optional<Collection> optCollection = collectionRepo.findByCode(collectionCode);
		
		Collection collection = optCollection.orElse(null);
		
		BOMInputStream bomInputStream =
		        BOMInputStream.builder()
		            .setInputStream(file.getInputStream())
		            .get();

		Reader reader = new InputStreamReader(bomInputStream);

	    Iterable<CSVRecord> records = CSVFormat.DEFAULT
	            .withFirstRecordAsHeader()
	            .parse(reader);

	    for (CSVRecord record : records) {
	        String number = record.get("number");
	        String code = record.get("code");
	        String name = record.get("name");
	        String imageUrl = record.get("image_url");
	        String rarity = record.get("rarity");

	        CollectibleItem newColItem = new CollectibleItem(number, code, name, imageUrl, rarity, collection, null, null);
	        collectibleItemRepo.save(newColItem);
	    }

	    return collectibleItemRepo.findAll();
	}
	
	@GetMapping("/user")
	public List<UserDtoResponse> getAllUsers() {
		return userMapper.toResponseList(userRepo.findAll());
	}

}
