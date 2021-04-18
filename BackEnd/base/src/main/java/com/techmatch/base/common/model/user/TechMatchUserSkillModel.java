package com.techmatch.base.common.model.user;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillResultEntity;
import com.techmatch.base.common.entity.user.TechMatchUserDescribeSkillEntity;

import lombok.Data;

@Data
public class TechMatchUserSkillModel {
	private String userId;;
	private String skillCd;
	private String skillName;
	private String skillJenreCd;
	private String skillJenreName;
	private String skillLevelCd;
	private String skillLevelDescription;
	public TechMatchUserSkillModel(TechMatchUserDescribeSkillEntity entity) {
		this.userId = entity.getUserId();
	    this.skillCd =entity.getSkillCd();
	    this.skillName = entity.getSkillName();
	    this.skillJenreCd = entity.getSkillJenreCd();
	    this.skillJenreName = entity.getSkillJenreName();
	    this.skillLevelCd = entity.getSkillLevelCd();
	    this.skillLevelDescription = entity.getSkillLevelDescription();
	}
}
