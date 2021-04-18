package com.techmatch.base.common.utility;

public enum TechMatchSkillTypeEnum {
	FRONT_END("TMSJ001"),
	BACK_END("TMSJ002"),
	INFRASTRUCTURE("TMSJ005"),
	MACHINE_LEARNING("TMSJ004"),
	NATIVE_APPLICATION("TMSJ003");
	
	private final String jenreCd;
	
	 TechMatchSkillTypeEnum(String jenreCd) {
		this.jenreCd = jenreCd;
	}
	 
	 public String getJenreCd() {
		 return this.jenreCd;
	 }
}