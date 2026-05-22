package com.swapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
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
@Table(name = "sp_collection")
public class Collection extends RecBase{
	private String name;
	private String code;
	private String type;
	
	@OneToMany(mappedBy = "collection")
	List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
	
	@OneToMany(mappedBy = "collection")
    @JsonManagedReference
	List<CollectibleItem> collectibleItems = new ArrayList<>();
}
