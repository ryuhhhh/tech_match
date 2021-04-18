package com.techmatch.base.common.model.requirement;

import java.util.List;

import lombok.Data;

@Data
public class TechMatchRequirementAndSkillsModel {
	TechMatchRequirementModel requirementModel;
	boolean adoption;
	List<TechMatchRequirementSkillModel> frontEndSkills;
	List<TechMatchRequirementSkillModel> backEndSkills;
	List<TechMatchRequirementSkillModel> infraSkills;
	List<TechMatchRequirementSkillModel> mlSkills;
	List<TechMatchRequirementSkillModel> nativeApplicationSkills;
}
