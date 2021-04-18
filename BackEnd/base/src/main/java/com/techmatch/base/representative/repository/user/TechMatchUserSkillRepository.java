package com.techmatch.base.representative.repository.user;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techmatch.base.common.entity.skill.TechMatchUserSkillEntity;
import com.techmatch.base.common.entity.skill.TechMatchUserSkillKey;

public interface TechMatchUserSkillRepository extends JpaRepository<TechMatchUserSkillEntity, TechMatchUserSkillKey>{

	@Transactional
	@Modifying
	@Query("DELETE FROM TechMatchUserSkillEntity us WHERE us.userSkillId.userId = :userId")
	public void deleteUserSKills(@Param("userId")String userId);
}
