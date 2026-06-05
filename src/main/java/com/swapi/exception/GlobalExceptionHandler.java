package com.swapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swapi.trade.ItemOwnershipException;
import com.swapi.tradeParticipant.ParticipantStatusUpdateNotAllowedException;


@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
    		RecordNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage()));
    }
	
	@ExceptionHandler(ItemOwnershipException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
    		ItemOwnershipException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }

	@ExceptionHandler(ParticipantStatusUpdateNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
    		ParticipantStatusUpdateNotAllowedException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
