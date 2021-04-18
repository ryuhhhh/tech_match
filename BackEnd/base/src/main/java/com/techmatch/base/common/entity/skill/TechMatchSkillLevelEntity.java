package com.techmatch.base.common.entity.skill;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="tm_skill_levels")
public class TechMatchSkillLevelEntity {
	@Id
	 private String skillLevelCd;
	 private String description;
}
