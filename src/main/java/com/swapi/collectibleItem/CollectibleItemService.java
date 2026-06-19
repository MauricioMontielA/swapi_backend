package com.swapi.collectibleItem;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.swapi.collectibleItem.dto.CollectibleItemBasicDto;
import com.swapi.collectibleItem.dto.CollectibleItemDtoResponse;
import com.swapi.collection.Collection;
import com.swapi.collection.CollectionRepository;
import com.swapi.model.auxiliar.TradeParticipantStatus;
import com.swapi.model.auxiliar.TradeStatus;
import com.swapi.trade.Trade;
import com.swapi.trade.dto.TradeDtoResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CollectibleItemService {
	private final CollectibleItemRepository collectibleItemRepo;
	private final CollectibleItemMapper collectibleItemMapper;
	private final CollectionRepository collectionRepo;

	public List<CollectibleItemBasicDto> getCollectibleItemByCollectionOpt(Long collectionId) {
		List<CollectibleItem> items = null;
		if (collectionId != null) {
			items = collectibleItemRepo.findByCollectionId(collectionId);
		} else {
			items = collectibleItemRepo.findAll();
		}

		return items.stream().map(item -> collectibleItemMapper.toBasicResponse(item)).toList();
	}
	
	public Page<CollectibleItemBasicDto> getCollectibleItemByCollectionPageable(Long collectionId, String name, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<CollectibleItem> collectibleItemsPage = null;
		if(name == null || name.isEmpty()) {
			collectibleItemsPage = collectibleItemRepo.findByCollectionId(collectionId, pageable);
		}else {
			collectibleItemsPage = collectibleItemRepo.findByCollectionIdAndNameContainingIgnoreCase(collectionId, name, pageable);
		}
		
		return collectibleItemsPage.map(collectibleItemMapper::toBasicResponse);
	}
	
	
	
	
//	public List<CollectibleItemBasicDto> getCollectibleItemByCollectionPageable(Long collectionId) {
//		List<CollectibleItem> items = null;
//		if (collectionId != null) {
//			items = collectibleItemRepo.findByCollectionId(collectionId);
//		} else {
//			items = collectibleItemRepo.findAll();
//		}
//
//		return items.stream().map(item -> collectibleItemMapper.toBasicResponse(item)).toList();
//	}
	
	public List<CollectibleItemBasicDto> searchByName(Long collectionId) {
		List<CollectibleItem> items = null;
		if (collectionId != null) {
			items = collectibleItemRepo.findByCollectionId(collectionId);
		} else {
			items = collectibleItemRepo.findAll();
		}

		return items.stream().map(item -> collectibleItemMapper.toBasicResponse(item)).toList();
	}

	
	
	
	
	
	public List<CollectibleItemDtoResponse> uploadItemsForCollectionByCsv(MultipartFile file, String collectionCode) throws IOException {
		Optional<Collection> optCollection = collectionRepo.findByCode(collectionCode);

		Collection collection = optCollection.orElse(null);

		BOMInputStream bomInputStream = BOMInputStream.builder().setInputStream(file.getInputStream()).get();

		Reader reader = new InputStreamReader(bomInputStream);

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

		for (CSVRecord record : records) {
			String number = record.get("number");
			String code = record.get("code");
			String name = record.get("name");
			String imageUrl = record.get("image_url");
			String rarity = record.get("rarity");

			CollectibleItem newColItem = new CollectibleItem(number, code, name, imageUrl, rarity, collection, null,
					null);
			collectibleItemRepo.save(newColItem);
		}

		return collectibleItemRepo.findAll().stream().map(item -> {
			CollectibleItemDtoResponse dto = collectibleItemMapper.toResponse(item);
			return dto;
		}).toList();
	}
}
