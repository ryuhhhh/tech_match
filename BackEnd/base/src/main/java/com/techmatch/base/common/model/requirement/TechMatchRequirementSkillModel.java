package com.techmatch.base.common.model.requirement;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillResultEntity;

import lombok.Data;

@Data
public class TechMatchRequirementSkillModel {
	private Integer id;;
	private String skillCd;
	private String skillName;
	private String skillJenreCd;
	private String skillJenreName;
	private String skillLevelCd;
	private String skillLevelDescription;
	public TechMatchRequirementSkillModel(TechMatchRequirementSkillResultEntity entity) {
		this.id = entity.getId();
	    this.skillCd =entity.getSkillCd();
	    this.skillName = entity.getSkillName();
	    this.skillJenreCd = entity.getSkillJenreCd();
	    this.skillJenreName = entity.getSkillJenreName();
	    this.skillLevelCd = entity.getSkillLevelCd();
	    this.skillLevelDescription = entity.getSkillLevelDescription();
	}
}
