package com.swapi.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.swapi.auth.dto.AuthProvider;
import com.swapi.model.auxiliar.Badget;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Table(name = "sp_user")
public class User extends RecBase{
	@Column(nullable = false, unique = true)
	private String email;
	private String username;
	@Column(name = "password_hash")
	private String passwordHash;
	@Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthProvider authProvider;
	@Column(name = "provider_user_id")
	private String providerUserId;
	@Column(name = "profile_image_url")
	private String profileImageUrl;
	@Enumerated(EnumType.STRING)
    @Column(name = "badget", nullable = false)
	private Badget badget;
	@Column(name = "rating", nullable = false)
	private double rating;
	
	@OneToMany(mappedBy = "user")
	Set<TradeParticipant> tradeParticipants = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	Set<UserCollectible> userCollectibles = new HashSet<>();
	
	@Override
	protected void onCreate() {
		super.onCreate();
		if (this.authProvider == null) {
            this.authProvider = AuthProvider.LOCAL;
        }
	}
}
