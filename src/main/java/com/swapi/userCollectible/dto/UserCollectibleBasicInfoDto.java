package com.swapi.userCollectible.dto;

import lombok.Data;

@Data
public class UserCollectibleBasicInfoDto {
	private Long userCollectibleId;
	private String collectibleItemNumber;
	private String collectibleItemName;
	private String collectibleItemImageUrl;
}
