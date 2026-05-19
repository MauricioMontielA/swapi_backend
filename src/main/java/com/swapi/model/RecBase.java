package com.swapi.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
public class RecBase {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    @Column(nullable = false, updatable = false, name = "created_at")
	private Timestamp createdAt;
	@Column(name = "modified_at")
	private Timestamp modifiedAt;
	
    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = new Timestamp(System.currentTimeMillis());
    }
}
