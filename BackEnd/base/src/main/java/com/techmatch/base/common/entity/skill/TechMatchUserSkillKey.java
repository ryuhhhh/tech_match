package com.techmatch.base.common.entity.skill;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class TechMatchUserSkillKey implements Serializable{
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "user_id")
	private String userId;
	@Basic(optional = false)
	@Column(name = "skill_cd")
	private String skillCd;
	
	public TechMatchUserSkillKey() {
		
	}
	
	public TechMatchUserSkillKey(String skillCd,String userId) {
		this.skillCd = skillCd;
		this.userId = userId;
	}
}
