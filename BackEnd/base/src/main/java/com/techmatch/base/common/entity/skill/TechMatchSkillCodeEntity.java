package com.techmatch.base.common.entity.skill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name="tm_skill_codes")
@Data
@Entity
public class TechMatchSkillCodeEntity {
	@Id
	@Column(name="skill_cd")
	private String skillCd;
	@Column(name="skill_jenre_cd")
	private String skillJenreCd;
	private String name;

	@ManyToOne
	@JoinColumn(name="skill_jenre_cd",insertable = false, updatable = false)
	TechMatchSkillJenreCodeEntity techMatchSkillJenreCodeEntity;
}
