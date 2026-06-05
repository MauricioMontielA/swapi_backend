package com.swapi.attributeDefinition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swapi.collectibleItemAttribute.CollectibleItemAttribute;
import com.swapi.collection.Collection;
import com.swapi.model.RecBase;

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
@Table(name = "sp_attributeDefinition")
public class AttributeDefinition extends RecBase{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collection_id")
	private Collection collection;
	private String key;
	private String label;
	@Column(name = "data_type")
	private String dataType;
	@Column(name = "is_filterable")
	private boolean isFilterable;
	@Column(name = "is_required")
	private boolean isRequired;
	
	@OneToMany(mappedBy = "attributeDefinition")
	Set<CollectibleItemAttribute> collectibleItemAtributes = new HashSet<>();
	
}
