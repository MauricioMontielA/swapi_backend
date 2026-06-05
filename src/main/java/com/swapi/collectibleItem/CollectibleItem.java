package com.swapi.collectibleItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swapi.collectibleItemAttribute.CollectibleItemAttribute;
import com.swapi.collection.Collection;
import com.swapi.model.RecBase;
import com.swapi.userCollectible.UserCollectible;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "sp_collectibleItem")
public class CollectibleItem extends RecBase{
	private String number;
	private String code;
	private String name;
	@Column(name = "image_url")
	private String imageUrl;
	private String rarity;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collection_id")
    @JsonBackReference
	private Collection collection;
	
	@OneToMany(mappedBy = "collectibleItem")
	Set<CollectibleItemAttribute> collectibleItemAtributes = new HashSet<>();
	
	@OneToMany(mappedBy = "collectibleItem")
	Set<UserCollectible> userCollectibles = new HashSet<>();
}
