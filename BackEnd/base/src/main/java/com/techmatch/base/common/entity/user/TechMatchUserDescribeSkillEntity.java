package com.techmatch.base.common.entity.user;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TechMatchUserDescribeSkillEntity {
	@Id
	private String userId;
	private String skillCd;
	private String skillName;
	private String skillJenreCd;
	private String skillJenreName;
	private String skillLevelCd;
	private String skillLevelDescription;
	public TechMatchUserDescribeSkillEntity(Object[] objects) {
		this.userId  = (String) objects[0];
	    this.skillCd = (String)objects[1];
	    this.skillName = (String)objects[2];
	    this.skillJenreCd = (String)objects[3];
	    this.skillJenreName = (String)objects[4];
	    this.skillLevelCd = (String)objects[5];
	    this.skillLevelDescription = (String)objects[6];
	}
}
