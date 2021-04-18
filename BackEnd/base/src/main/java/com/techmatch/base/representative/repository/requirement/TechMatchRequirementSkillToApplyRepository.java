package com.techmatch.base.representative.repository.requirement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillEntity;

@Repository
public interface TechMatchRequirementSkillToApplyRepository extends JpaRepository<TechMatchRequirementSkillEntity, Integer>{

}
