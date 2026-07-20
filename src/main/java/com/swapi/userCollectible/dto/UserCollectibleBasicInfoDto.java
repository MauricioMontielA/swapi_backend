package com.swapi.userCollectible.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectibleBasicInfoDto {
	private Number userCollectibleId;
	private String collectibleItemNumber;
	private String collectibleItemName;
	private String collectibleItemImageUrl;

}
