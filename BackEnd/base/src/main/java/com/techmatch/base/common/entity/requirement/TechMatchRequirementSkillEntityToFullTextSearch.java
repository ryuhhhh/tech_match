package com.techmatch.base.common.entity.requirement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.techmatch.base.common.entity.skill.TechMatchSkillCodeEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillLevelEntity;

import lombok.Data;

@Data
@Entity
@Table(name="tm_requirement_skills")
public class TechMatchRequirementSkillEntityToFullTextSearch {
	@Id
	@Column(name="requirement_skill_id")
	private Integer requirementSkillId;
	@Column(name="requirement_id")
	private int id;
	@Column(name="skill_cd")
	private String skillCd;
	@Column(name="skill_level_cd")
	private String skillLevelCd;
}
