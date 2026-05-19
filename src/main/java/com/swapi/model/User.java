package com.swapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
	@Column(name = "auth_provider")
	private String authProvider;
	@Column(name = "provider_user_id")
	private String providerUserId;
	@Column(name = "profile_image_url")
	private String profileImageUrl;
	
	@OneToMany(mappedBy = "user")
	List<TradeParticipant> tradeParticipants = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	List<UserCollectible> userCollectibles = new ArrayList<>();
}
