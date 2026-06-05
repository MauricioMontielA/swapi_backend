package com.swapi.goal;

import java.util.Set;

import com.swapi.collection.Collection;
import com.swapi.model.RecBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sp_goal")
public class Goal extends RecBase{
    @Column(name = "name", nullable = false)
	private String name;
    @Column(name = "description", nullable = false)
	private String description;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collection_id")
	private Collection collection;
	
}
