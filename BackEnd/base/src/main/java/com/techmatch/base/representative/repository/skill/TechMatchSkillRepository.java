package com.techmatch.base.representative.repository.skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.skill.TechMatchSkillCodeEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillJenreCodeEntity;

@Repository
public interface TechMatchSkillRepository  extends JpaRepository<TechMatchSkillCodeEntity, String>{

	@Query("SELECT ce FROM TechMatchSkillCodeEntity ce WHERE ce.skillJenreCd = :skillType")
	public List<TechMatchSkillCodeEntity> getSKillMaster(@Param("skillType")String skillType);
}
