package com.techmatch.base.common.model.auth;

import lombok.Data;

@Data
public class TechMatchAuthenticationRequestModel {
	private String userId;
	private String password;
	public TechMatchAuthenticationRequestModel(String userId,String password) {
		this.userId=userId;
		this.password=password;
	}
}
