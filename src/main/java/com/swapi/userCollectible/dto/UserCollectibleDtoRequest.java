package com.swapi.userCollectible.dto;

import java.util.List;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCollectibleDtoRequest {
	private List<Long> collectibleItemIds;
	private Long collectionId;
}
