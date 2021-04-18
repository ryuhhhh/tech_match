package com.techmatch.base.common.entity.requirement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.techmatch.base.common.entity.skill.TechMatchSkillCodeEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillLevelEntity;

import lombok.Data;

@Data
@Entity
@Table(name="tm_requirement_skills")
public class TechMatchRequirementSkillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requirement_skill_id_generator")
	@SequenceGenerator(name = "requirement_skill_id_generator", sequenceName = "tm_requirement_skills_requirement_skill_id_seq", allocationSize = 1)
	@Column(name="requirement_skill_id")
	private Integer requirementSkillId;
	@Column(name="requirement_id")
	private int id;
	@Column(name="skill_cd")
	private String skillCd;
	@Column(name="skill_level_cd")
	private String skillLevelCd;

	@ManyToOne
	@JoinColumn(name="requirement_id",insertable = false, updatable = false)
	TechMatchRequirementEntity  techMatchRequirementEntitiy;

	@ManyToOne
	@JoinColumn(name="skill_cd",insertable = false, updatable = false)
	TechMatchSkillCodeEntity techMatchSkillCodeEntity;

	@ManyToOne
	@JoinColumn(name="skill_level_cd",insertable = false, updatable = false)
	TechMatchSkillLevelEntity techMatchSkillLevelEntity;

}
