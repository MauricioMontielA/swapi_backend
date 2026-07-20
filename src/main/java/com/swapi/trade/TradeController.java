package com.swapi.trade;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.CustomUserPrincipal;
import com.swapi.trade.dto.TradeCandidateItemsDto;
import com.swapi.trade.dto.TradeDtoRequest;
import com.swapi.trade.dto.TradeDtoResponse;
import com.swapi.trade.dto.TradeGeneralViewResDto;
import com.swapi.userCollectible.dto.UserColMatchInfoRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {
	private final TradeService tradeService;
	
	@PostMapping
	public TradeDtoResponse createTrade(@AuthenticationPrincipal CustomUserPrincipal user, @RequestBody TradeDtoRequest dtoReq) {
		return tradeService.createTrade(user.getId(), dtoReq);

	}
	
	@GetMapping()
	public Page<TradeGeneralViewResDto> getTrades(
			@AuthenticationPrincipal CustomUserPrincipal user,
			@RequestParam String status,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "20") int size
	) {
		return tradeService.getTradesByStatus(user.getId(), status, page, size);
	}
	
	@PostMapping("/proposal-items")
	public TradeCandidateItemsDto getTradeProposalItems(@AuthenticationPrincipal CustomUserPrincipal user,
			@RequestBody UserColMatchInfoRequestDto request) {
		return tradeService.getCandidateItemsInfoByTargetUser(user.getId(), request);
	}
}
