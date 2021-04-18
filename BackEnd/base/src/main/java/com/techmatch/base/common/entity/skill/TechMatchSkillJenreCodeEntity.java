package com.techmatch.base.common.entity.skill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name="tm_skill_jenre_codes")
@Data
@Entity
public class TechMatchSkillJenreCodeEntity {
	@Id
	@Column(name="skill_jenre_cd")
	 private String skillJenreCd;
	 private String name;
}
