package com.swapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sp_listing")
public class Listing extends RecBase{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_collectible_id")
	private UserCollectible userCollectible;
	@Column(name = "listing_type")
	private String listingType;
	private int quantity;
	@Column(name = "price_amount")
	private double priceAmount;
	@Column(name = "price_currency")
	private String priceCurrency;
	private String description;
	private String status;
}
