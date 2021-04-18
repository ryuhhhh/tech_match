package com.techmatch.base.common.model.auth;

import lombok.Data;

@Data
public class TechMatchAuthenticationResponseModel {
	private final String jwtToken;

	public TechMatchAuthenticationResponseModel(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}
	
}
