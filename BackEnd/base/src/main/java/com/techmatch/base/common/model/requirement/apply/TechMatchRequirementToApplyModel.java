package com.techmatch.base.common.model.requirement.apply;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TechMatchRequirementToApplyModel {
	@Size(max=50)
	@NotNull
	private String userId;
	@Size(max=100)
	@NotNull
	private String title;
	@Size(max=500)
	@NotNull
	private String whyText;
	@Size(max=500)
	@NotNull
	private String whatText;
	@Size(max=500)
	@NotNull
	private String howText;
	@Size(max=100)
	private String image_1;
	@Size(max=100)
	private String image_2;
	@Max(100)
	private int requiredNum;
	@Future
	private java.util.Date targetDate;
	@Future
	private java.util.Date expireDate;
	
}
