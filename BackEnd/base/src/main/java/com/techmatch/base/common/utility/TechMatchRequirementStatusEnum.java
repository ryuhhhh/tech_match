package com.techmatch.base.common.utility;

import lombok.Data;

public enum TechMatchRequirementStatusEnum {
	NOW("RS001","募集中"),
	CLOSE("RS002","募集済み"),
	CANCELD("RS003","取り止め");
	TechMatchRequirementStatusEnum(String statusCd,String name) {
		this.statusCd = statusCd;
		this.name=name;
	}
	private String statusCd;
	private String name;
	
	public  String getStatusCd() {
		return this.statusCd;
	}
}