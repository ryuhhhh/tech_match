package com.techmatch.base.common.entity.skill;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;

import lombok.Data;

@Table(name="tm_user_skills")
@Data
@Entity
public class TechMatchUserSkillEntity {

	@EmbeddedId
	private TechMatchUserSkillKey userSkillId;
	
	@Column(name="skill_level_cd")
	private String skillLevelCd;

	@ManyToOne
	@JoinColumn(name="skill_cd",insertable = false, updatable = false)
	TechMatchSkillCodeEntity techMatchSkillCodeEntity;

	@ManyToOne
	@JoinColumn(name="skill_level_cd",insertable = false, updatable = false)
	TechMatchSkillLevelEntity techMatchSkillLevelEntity;
	
	public TechMatchUserSkillEntity() {
	}

	public TechMatchUserSkillEntity(TechMatchUserSkillKey userSkillId) {
		this.userSkillId = userSkillId;
	}
	
	public TechMatchUserSkillEntity(TechMatchUserSkillKey userSkillId,String skillLevelCd) {
		this.userSkillId = userSkillId;
		this.skillLevelCd = skillLevelCd;
	}
}
