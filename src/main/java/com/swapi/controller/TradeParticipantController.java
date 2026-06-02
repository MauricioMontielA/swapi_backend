package com.swapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swapi.auth.dto.CustomUserPrincipal;
import com.swapi.dto.TradeDtoRequest;
import com.swapi.repositories.TradeParticipantRepository;
import com.swapi.services.TradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/participant")
@RequiredArgsConstructor
public class TradeParticipantController {
	private final TradeService tradeService;

	@PutMapping("/change-status/{participantId}")
	public ResponseEntity<Map<String, String>> changeStatusTrade(@AuthenticationPrincipal CustomUserPrincipal user, 
	        @PathVariable Long participantId, @RequestParam String status) {
		Map<String, String> response = new HashMap<>();
		try {
			tradeService.changeParticipantStatus(user.getId(), participantId, status);
		    response.put("message", "OK");
		    return ResponseEntity.ok(response);
		} catch (Exception e) {
			// TODO: handle exception
		    response.put("message", "Error");
		    return ResponseEntity.internalServerError().body(response);
		}
	}
}
