package com.swapi.user.dto;

import java.time.LocalDate;

import com.swapi.model.auxiliar.Badge;

import lombok.Data;

@Data
public class UserDetailsDtoResponse {
	private Long id;
	private String username;
	private String profileImageUrl;
	private Badge badge;
	private int swapsQuantity;
	private double rating;
	private int collectionQuantity;
	private LocalDate profileCreationDate;
}
