package com.swapi.collectibleItemAttribute;

import java.sql.Timestamp;

import com.swapi.attributeDefinition.AttributeDefinition;
import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.model.RecBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "sp_collectibleItemAttribute")
public class CollectibleItemAttribute extends RecBase{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collectible_item_id")
	private CollectibleItem collectibleItem;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attribute_definition_id")
	private AttributeDefinition attributeDefinition;
	@Column(name = "value_text")
	private String valueText;
	@Column(name = "value_number")
	private Number valueNumber;
	@Column(name = "value_boolean")
	private boolean valueBoolean;
	@Column(name = "value_date")
	private Timestamp valueDate;
}
