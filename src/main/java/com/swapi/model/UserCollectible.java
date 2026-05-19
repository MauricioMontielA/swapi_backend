package com.swapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sp_userCollectible")
public class UserCollectible extends RecBase{
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "collectible_item_id")
	private CollectibleItem collectibleItem;
	private int quantity;
	private String notes;
	@Column(name = "is_for_trade")
	private boolean isForTrade;
	@Column(name = "is_for_sale")
	private boolean isForSale;
	
	@OneToMany(mappedBy = "userCollectible")
	List<Listing> listings = new ArrayList<>();
	
	@OneToMany(mappedBy = "userCollectible")
	List<TradeItem> tradeItems = new ArrayList<>();
}
