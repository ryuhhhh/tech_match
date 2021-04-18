package com.techmatch.base.representative.repository.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.user.TechMatchUserDescribeSkillEntity;

@Repository
public interface TechMatchUserDescribeSkillRepository extends JpaRepository<TechMatchUserDescribeSkillEntity,String>{

	@Query(value="SELECT us.userSkillId.userId,tsc.skillCd,tsc.name,tsjc.skillJenreCd,tsjc.name,us.skillLevelCd,tsl.description FROM TechMatchUserSkillEntity us "+
		                	"INNER JOIN TechMatchSkillCodeEntity tsc ON us.userSkillId.skillCd = tsc.skillCd "+
		                	"INNER JOIN TechMatchSkillJenreCodeEntity tsjc ON tsc.skillJenreCd=tsjc.skillJenreCd "+
		                	"INNER JOIN TechMatchSkillLevelEntity tsl ON  us.skillLevelCd=tsl.skillLevelCd "+
		                	"WHERE us.userSkillId.userId = :userId AND tsc.skillJenreCd=:skillType")
	public List<Object[]> getUserSkillsInternal(@Param("userId")String userId,@Param("skillType")String skillType);
	
	default List<TechMatchUserDescribeSkillEntity> getUserSKills(String userId,String skillType){
		return getUserSkillsInternal(userId, skillType)
		.stream()
		.map(TechMatchUserDescribeSkillEntity::new)
		.collect(Collectors.toList());
	}
}
