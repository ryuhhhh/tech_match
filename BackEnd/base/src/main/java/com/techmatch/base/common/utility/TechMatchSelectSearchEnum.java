package com.techmatch.base.common.utility;

public enum TechMatchSelectSearchEnum {
	SKILL_CD("skillCd"),
	SKILL_LEVEL("skillLevel"),
	EXPIRE_DATE("expireDate");
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;
	TechMatchSelectSearchEnum(String name) {
		this.name=name;
	}
}
