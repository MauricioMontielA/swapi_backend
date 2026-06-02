package com.swapi.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swapi.dto.TradeAttributesDto;
import com.swapi.dto.TradeDtoRequest;
import com.swapi.dto.TradeDtoResponse;
import com.swapi.mapper.CollectibleItemMapper;
import com.swapi.mapper.TradeMapper;
import com.swapi.mapper.UserCollectibleMapper;
import com.swapi.model.Trade;
import com.swapi.model.TradeItem;
import com.swapi.model.TradeParticipant;
import com.swapi.model.UserCollectible;
import com.swapi.model.auxiliar.TradeParticipantStatus;
import com.swapi.model.auxiliar.TradeStatus;
import com.swapi.repositories.CollectibleItemRepository;
import com.swapi.repositories.TradeItemRepository;
import com.swapi.repositories.TradeParticipantRepository;
import com.swapi.repositories.TradeRepository;
import com.swapi.repositories.UserCollectibleRepository;
import com.swapi.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {
	private final UserCollectibleService userColService;
	private final TradeRepository tradeRepo;
	private final TradeItemRepository tradeItemRepo;
	private final TradeParticipantRepository tradeParticipantRepo;
	private final TradeMapper tradeMapper;


	@Transactional
	public void createTrade(Long userId, TradeDtoRequest dtoRequest) {
		if (dtoRequest == null || dtoRequest.getItemsToTrade() == null || dtoRequest.getItemsToTrade().size() < 2) {
			return;
		}
		List<TradeAttributesDto> tradesAtts = dtoRequest.getItemsToTrade();

		List<UserCollectible> userColList = userColService
				.getUserCollectibleByIdIn(getList(tradesAtts, TradeAttributesDto::getItem));
		if (userColList == null) {
			return;
		}

		boolean isOwner = false;
		Map<Long, TradeParticipant> mapUserIdParticipant = new HashMap<>();

		Trade trade = new Trade();
		trade.setStatus(TradeStatus.PROPOSED);
		tradeRepo.save(trade);

		for (UserCollectible userCollectible : userColList) {
			if (userColService.isOwner(userCollectible, userId)) {
				isOwner = true;
			}
			if (mapUserIdParticipant.get(userCollectible.getUser().getId()) != null) {
				continue;
			}
			TradeParticipant tradeParticipant = new TradeParticipant();
			tradeParticipant.setTrade(trade);
			tradeParticipant.setUser(userCollectible.getUser());
			boolean isRequester = userCollectible.getUser().getId() == userId;
			tradeParticipant.setStatus(isRequester ? TradeParticipantStatus.PROPOSED : TradeParticipantStatus.PENDING);
			tradeParticipantRepo.save(tradeParticipant);
			mapUserIdParticipant.put(userCollectible.getUser().getId(), tradeParticipant);
		}

		if (!isOwner) {
			throw new RuntimeException("User that make the request isn't owner of any item involve");
		}

		for (UserCollectible userCollectible : userColList) {
			TradeAttributesDto tradeAtt = tradesAtts.stream().filter(tr -> tr.getItem() == userCollectible.getId())
					.findFirst().get();
			TradeItem tradeItem = new TradeItem();
			tradeItem.setFromParticipant(mapUserIdParticipant.get(tradeAtt.getFromParticipant()));
			tradeItem.setToParticipant(mapUserIdParticipant.get(tradeAtt.getToParticipant()));
			tradeItem.setUserCollectible(userCollectible);
			tradeItem.setQuantity(tradeAtt.getQuantity());
			tradeItem.setTrade(trade);

			tradeItemRepo.save(tradeItem);
		}

	}

	public Page<TradeDtoResponse> getTradesByStatus(Long userId, String status, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Long> tradeIdsPage = null;
		
		if (status.equals(TradeStatus.CANCELLED.name()) || status.equals(TradeStatus.COMPLETED.name())) {
	        TradeStatus tradeStatus = TradeStatus.valueOf(status);

			tradeIdsPage = tradeRepo.findTradeIdsForStatusAndUser(tradeStatus, userId,  pageable);
		} else {
	        TradeParticipantStatus tradePartStatus = TradeParticipantStatus.valueOf(status);

			tradeIdsPage = tradeRepo.findTradeIdsForPartStatusAndUser(tradePartStatus, userId,
					List.of(TradeStatus.CANCELLED, TradeStatus.COMPLETED), pageable);
		}

		if (tradeIdsPage == null || tradeIdsPage.isEmpty()) {
			return Page.empty(pageable);
		}

		List<Trade> trades = tradeRepo.findTradesWithDetailsByIdIn(tradeIdsPage.getContent());
		List<TradeDtoResponse> tradesResponse = trades.stream()
			.map(trade ->{
				TradeDtoResponse tradeDto = tradeMapper.toResponse(trade, userId);
				return tradeDto;
			})
			.toList();

		return new PageImpl<>(tradesResponse, pageable, tradesResponse.size());
	}
	
	@Transactional
	public void changeParticipantStatus(Long userId, Long participantId, String status) {
		Optional<TradeParticipant> optParticipant = tradeParticipantRepo.findById(participantId);
		if (!optParticipant.isPresent() ) {
			return;
		}
		TradeParticipant participant = optParticipant.get();

		if (participant.getUser().getId() != userId || 
				!participant.getStatus().equals(TradeParticipantStatus.PENDING)) {
			return;
		}
		
		Trade trade = participant.getTrade();
        TradeParticipantStatus participantStatus = TradeParticipantStatus.valueOf(status.toUpperCase());

        participant.setStatus(participantStatus);
        
        if (participant.getStatus().equals(TradeParticipantStatus.APPROVED)) {
        	boolean allAccepted = true;
            
            for(TradeParticipant tradePart : trade.getTradeParticipants()) {
            	if (!tradePart.getStatus().equals(TradeParticipantStatus.APPROVED) && !tradePart.getStatus().equals(TradeParticipantStatus.PROPOSED)) {
            		allAccepted = false;
            		break;
    			}
            }
            
            if (allAccepted) {
            	trade.setStatus(TradeStatus.ACCEPTED);
    		}
		}else if(participant.getStatus().equals(TradeParticipantStatus.REJECTED)) {
        	trade.setStatus(TradeStatus.REJECTED);
		}
        
        tradeParticipantRepo.save(participant);
        tradeRepo.save(trade);
	}

	private <T> List<T> getList(List<TradeAttributesDto> tradeAtts, Function<TradeAttributesDto, T> mapper) {
		return tradeAtts.stream().map(mapper).toList();
	}
}
