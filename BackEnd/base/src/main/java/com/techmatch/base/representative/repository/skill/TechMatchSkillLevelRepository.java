package com.techmatch.base.representative.repository.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.skill.TechMatchSkillLevelEntity;

@Repository
public interface TechMatchSkillLevelRepository extends JpaRepository<TechMatchSkillLevelEntity, String>{

}
