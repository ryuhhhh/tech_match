package com.techmatch.base.common.model.requirement;

import lombok.Data;

@Data
public class TechMatchRequirementModel {
	 private int id;
	 private String userId;
	 private String title;
	 private String whyText;
	 private String whatText;
	 private String howText;
	 private String image_1;
	 private String image_2;
	 private Integer requirementNum;
	 // ステータス(日本語)
	 private String requirementStatus;
	 private String expireDate;
	 private String targetDate;
	 private String createDate;
	 private String updateDate;
}
