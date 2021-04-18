package com.techmatch.base.common.model.requirement;

import lombok.Data;

@Data
public class TechMatchRequirementListModel {
	 private int id;
	 private String userId;
	 private String title;
	 // ステータス(日本語)
	 private String requirementStatus;
	 private String expireDate;

}
