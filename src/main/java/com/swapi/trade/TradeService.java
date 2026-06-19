package com.swapi.trade;

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

import com.swapi.collectibleItem.CollectibleItemMapper;
import com.swapi.collectibleItem.CollectibleItemRepository;
import com.swapi.collectibleItem.dto.CollectibleItemBasicDto;
import com.swapi.exception.BadRequestException;
import com.swapi.exception.RecordNotFoundException;
import com.swapi.model.auxiliar.TradeParticipantStatus;
import com.swapi.model.auxiliar.TradeStatus;
import com.swapi.trade.dto.TradeAttributesDto;
import com.swapi.trade.dto.TradeCandidateItemsDto;
import com.swapi.trade.dto.TradeDtoRequest;
import com.swapi.trade.dto.TradeDtoResponse;
import com.swapi.tradeItem.TradeItem;
import com.swapi.tradeItem.TradeItemRepository;
import com.swapi.tradeParticipant.ParticipantStatusUpdateNotAllowedException;
import com.swapi.tradeParticipant.TradeParticipant;
import com.swapi.tradeParticipant.TradeParticipantRepository;
import com.swapi.user.UserRepository;
import com.swapi.userCollectible.UserCollectible;
import com.swapi.userCollectible.UserCollectibleMapper;
import com.swapi.userCollectible.UserCollectibleRepository;
import com.swapi.userCollectible.UserCollectibleService;
import com.swapi.userCollectible.dto.UserColMatchInfoRequestDto;
import com.swapi.userCollectible.dto.UserCollectibleBasicInfoDto;

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
	private final UserCollectibleRepository userCollRepo;


	@Transactional
	public TradeDtoResponse createTrade(Long userId, TradeDtoRequest dtoRequest) {
		if (dtoRequest == null || dtoRequest.getItemsToTrade() == null || dtoRequest.getItemsToTrade().size() < 2) {
			throw new BadRequestException("Missing information");
		}
		List<TradeAttributesDto> tradesAtts = dtoRequest.getItemsToTrade();

		List<UserCollectible> userColList = userColService
				.getUserCollectibleByIdIn(getList(tradesAtts, TradeAttributesDto::getUserCollectible));
		if (userColList == null) {
			throw new RecordNotFoundException("User items not found");
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
			throw new ItemOwnershipException("User that make the request isn't owner of any item involve");
		}

		for (UserCollectible userCollectible : userColList) {
			TradeAttributesDto tradeAtt = tradesAtts.stream().filter(tr -> tr.getUserCollectible() == userCollectible.getId())
					.findFirst().get();
			TradeItem tradeItem = new TradeItem();
			tradeItem.setFromParticipant(mapUserIdParticipant.get(tradeAtt.getFromUser()));
			tradeItem.setToParticipant(mapUserIdParticipant.get(tradeAtt.getToUser()));
			tradeItem.setUserCollectible(userCollectible);
			tradeItem.setQuantity(tradeAtt.getQuantity());
			tradeItem.setTrade(trade);

			tradeItemRepo.save(tradeItem);
			

		}

		return tradeMapper.toResponse(trade, userId);

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
			throw new RecordNotFoundException("Participant not found");
		}
		TradeParticipant participant = optParticipant.get();

		if (participant.getUser().getId() != userId || 
				!participant.getStatus().equals(TradeParticipantStatus.PENDING)) {
			throw new ParticipantStatusUpdateNotAllowedException("Participant status is different to pending");
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
	
	public TradeCandidateItemsDto getCandidateItemsInfoByTargetUser(Long userId, UserColMatchInfoRequestDto request) {
		if (userId == null || request == null) {
			return null;
		}
		
		List<UserCollectibleBasicInfoDto> desiredItemsForTarget = userCollRepo.getDesiredItemsFromTargetUser(userId, 
				request.getTargetUserId(), request.getCollectionId());
		
		int useFilter = request.getFilteredIds() == null || request.getFilteredIds().isEmpty() ? 0 : 1;
		List<Long> filteredIds = useFilter == 1 ? request.getFilteredIds() : List.of(-1L);
		List<UserCollectibleBasicInfoDto> offeredItemsToTarget = userCollRepo.getOfferableItemsToTargetUser(userId, 
				request.getTargetUserId(), request.getCollectionId(), useFilter, filteredIds);

		return new TradeCandidateItemsDto(desiredItemsForTarget, offeredItemsToTarget);
	}

	private <T> List<T> getList(List<TradeAttributesDto> tradeAtts, Function<TradeAttributesDto, T> mapper) {
		return tradeAtts.stream().map(mapper).toList();
	}
}
