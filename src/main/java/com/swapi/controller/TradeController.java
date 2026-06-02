package com.swapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.CustomUserPrincipal;
import com.swapi.dto.TradeDtoRequest;
import com.swapi.dto.TradeDtoResponse;
import com.swapi.model.Trade;
import com.swapi.services.CollectionService;
import com.swapi.services.TradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {
	private final TradeService tradeService;
	
	@PostMapping
	public ResponseEntity<Map<String, String>> createTrade(@AuthenticationPrincipal CustomUserPrincipal user, @RequestBody TradeDtoRequest dtoReq) {
		Map<String, String> response = new HashMap<>();
		try {
			tradeService.createTrade(user.getId(), dtoReq);
		    response.put("message", "OK");
		    return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
		    response.put("message", "Error");
		    return ResponseEntity.internalServerError().body(response);
		}
	}
	
	@GetMapping()
	public ResponseEntity<Page<TradeDtoResponse>> getTrades(
			@AuthenticationPrincipal CustomUserPrincipal user,
			@RequestParam String status,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "20") int size
	) {

	    return ResponseEntity.ok(
	        tradeService.getTradesByStatus(user.getId(), status, page, size)
	    );
	}
}
