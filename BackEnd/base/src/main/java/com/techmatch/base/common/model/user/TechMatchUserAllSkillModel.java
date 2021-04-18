package com.techmatch.base.common.model.user;

import java.util.List;

import lombok.Data;

@Data
public class TechMatchUserAllSkillModel {
	private String userId;
	private boolean isAdoption;
	List<TechMatchUserSkillModel> frontEndSkills;
	List<TechMatchUserSkillModel> backEndSkills;
	List<TechMatchUserSkillModel> infraSkills;
	List<TechMatchUserSkillModel> mlSkills;
	List<TechMatchUserSkillModel> nativeApplicationSkills;
}
