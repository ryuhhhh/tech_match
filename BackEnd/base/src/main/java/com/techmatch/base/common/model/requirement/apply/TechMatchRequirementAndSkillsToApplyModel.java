package com.techmatch.base.common.model.requirement.apply;

import java.util.List;

import lombok.Data;

@Data
public class TechMatchRequirementAndSkillsToApplyModel {
	TechMatchRequirementToApplyModel requirementModel;
	List<TechMatchRequirementSkillToApplyModel> frontEndSkills;
	List<TechMatchRequirementSkillToApplyModel> backEndSkills;
	List<TechMatchRequirementSkillToApplyModel> infraSkills;
	List<TechMatchRequirementSkillToApplyModel> mlSkills;
	List<TechMatchRequirementSkillToApplyModel> nativeApplicationSkills;
	
}
