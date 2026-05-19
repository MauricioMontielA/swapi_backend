package com.swapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "sp_collectibleItem")
public class CollectibleItem extends RecBase{
	private String number;
	private String code;
	private String name;
	@Column(name = "image_url")
	private String imageUrl;
	private String rarity;
	@ManyToOne
	@JoinColumn(name = "collection_id")
    @JsonBackReference
	private Collection collection;
	
	@OneToMany(mappedBy = "collectibleItem")
	List<CollectibleItemAttribute> collectibleItemAtributes = new ArrayList<>();
	
	@OneToMany(mappedBy = "collectibleItem")
	List<UserCollectible> userCollectibles = new ArrayList<>();
}
