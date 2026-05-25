package com.swapi.auth.model;

import lombok.Data;

@Data
public class GoogleUserInfo {
	private String sub;
    private String email;
    private String name;
    private String picture;
}
