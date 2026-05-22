package com.swapi.filter;

import lombok.Data;

@Data
public class UserCollectibleFilter {
	private Long userId;
    private Long collectionId;
    private String rarity;
    private Boolean forTrade;
    private Boolean forSale;
}
