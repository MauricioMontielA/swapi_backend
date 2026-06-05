package com.swapi.tradeParticipant;

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
import com.swapi.trade.TradeDtoRequest;
import com.swapi.trade.TradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/participant")
@RequiredArgsConstructor
public class TradeParticipantController {
	private final TradeService tradeService;

	@PutMapping("/change-status/{participantId}")
	public ResponseEntity<Void> changeStatusTrade(@AuthenticationPrincipal CustomUserPrincipal user, 
	        @PathVariable Long participantId, @RequestParam String status) {
		tradeService.changeParticipantStatus(user.getId(), participantId, status);
		return ResponseEntity.ok().build();
	}
}
