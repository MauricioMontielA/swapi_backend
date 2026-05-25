package com.swapi.model;

import java.util.ArrayList;
import java.util.List;

import com.swapi.auth.dto.AuthProvider;

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
	
	@OneToMany(mappedBy = "user")
	List<TradeParticipant> tradeParticipants = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	List<UserCollectible> userCollectibles = new ArrayList<>();
	
	@Override
	protected void onCreate() {
		super.onCreate();
		if (this.authProvider == null) {
            this.authProvider = AuthProvider.LOCAL;
        }
	}
}
