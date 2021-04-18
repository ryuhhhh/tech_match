package com.techmatch.base.common.entity.requirement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class TechMatchRequirementSkillResultEntity {
	@Id
	private int id;
	private String skillCd;
	private String skillName;
	private String skillJenreCd;
	private String skillJenreName;
	private String skillLevelCd;
	private String skillLevelDescription;
	public TechMatchRequirementSkillResultEntity(Object[] objects) {
		this.id  = (int) objects[0];
	    this.skillCd = (String)objects[1];
	    this.skillName = (String)objects[2];
	    this.skillJenreCd = (String)objects[3];
	    this.skillJenreName = (String)objects[4];
	    this.skillLevelCd = (String)objects[5];
	    this.skillLevelDescription = (String)objects[6];
	}
}
