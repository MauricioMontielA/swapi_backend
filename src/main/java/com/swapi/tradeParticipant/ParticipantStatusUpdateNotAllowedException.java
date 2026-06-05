package com.swapi.tradeParticipant;

public class ParticipantStatusUpdateNotAllowedException extends RuntimeException{
	public ParticipantStatusUpdateNotAllowedException(String message){
		super(message);
	}
}
