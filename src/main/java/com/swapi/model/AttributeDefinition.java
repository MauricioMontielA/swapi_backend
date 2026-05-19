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
@Table(name = "sp_attributeDefinition")
public class AttributeDefinition extends RecBase{
	@ManyToOne
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
	List<CollectibleItemAttribute> collectibleItemAtributes = new ArrayList<>();
	
}
