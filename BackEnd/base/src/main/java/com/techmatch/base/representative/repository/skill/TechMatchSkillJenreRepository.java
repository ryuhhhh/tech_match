package com.techmatch.base.representative.repository.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementStatusEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillJenreCodeEntity;

@Repository
public interface TechMatchSkillJenreRepository extends JpaRepository<TechMatchSkillJenreCodeEntity, String>{

}
