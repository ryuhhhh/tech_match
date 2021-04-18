package com.techmatch.base.common.entity.master;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementStatusEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillCodeEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillJenreCodeEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillLevelEntity;


public class MasterTableEntity {
	List<TechMatchRequirementStatusEntity> requirementStatusEntity;
	List<TechMatchSkillLevelEntity> skillLevelEntity;
	List<TechMatchSkillJenreCodeEntity> skillJenreCodeEntity;
	List<TechMatchSkillCodeEntity> fronEndSkillCodeEntity;
	List<TechMatchSkillCodeEntity> backEndSkillCodeEntity;
	List<TechMatchSkillCodeEntity> infraSkillCodeEntity;
	List<TechMatchSkillCodeEntity> mlSkillCodeEntity;
	List<TechMatchSkillCodeEntity> nativeApplicationSkillCodeEntity;
	public MasterTableEntity(List<TechMatchRequirementStatusEntity> requirementStatusEntity,
			List<TechMatchSkillLevelEntity> skillLevelEntity, List<TechMatchSkillJenreCodeEntity> skillJenreCodeEntity,
			List<TechMatchSkillCodeEntity> fronEndSkillCodeEntity,
			List<TechMatchSkillCodeEntity> backEndSkillCodeEntity, List<TechMatchSkillCodeEntity> infraSkillCodeEntity,
			List<TechMatchSkillCodeEntity> mlSkillCodeEntity,
			List<TechMatchSkillCodeEntity> nativeApplicationSkillCodeEntity) {
		super();
		this.requirementStatusEntity = requirementStatusEntity;
		this.skillLevelEntity = skillLevelEntity;
		this.skillJenreCodeEntity = skillJenreCodeEntity;
		this.fronEndSkillCodeEntity = fronEndSkillCodeEntity;
		this.backEndSkillCodeEntity = backEndSkillCodeEntity;
		this.infraSkillCodeEntity = infraSkillCodeEntity;
		this.mlSkillCodeEntity = mlSkillCodeEntity;
		this.nativeApplicationSkillCodeEntity = nativeApplicationSkillCodeEntity;
	}
}
