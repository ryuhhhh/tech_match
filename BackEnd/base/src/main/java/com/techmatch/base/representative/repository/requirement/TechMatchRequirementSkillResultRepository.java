package com.techmatch.base.representative.repository.requirement;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.SqlResultSetMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillResultEntity;
import com.techmatch.base.common.entity.user.TechMatchUserEntity;
import com.techmatch.base.common.utility.TechMatchSkillTypeEnum;

@Repository
public interface TechMatchRequirementSkillResultRepository extends JpaRepository<TechMatchRequirementSkillResultEntity, Integer>{

	/**
	 * スキル情報を取得します
	 * @param id 募集情報ID
	 * @param skillType
	 */
	@Query(value="SELECT tri.id,tsc.skillCd,tsc.name,tsjc.skillJenreCd,tsjc.name,trs.skillLevelCd,tsl.description FROM TechMatchRequirementEntity tri "+
              "JOIN TechMatchRequirementSkillEntity trs ON tri.id=trs.id "+
		      "INNER JOIN TechMatchSkillCodeEntity tsc ON trs.skillCd = tsc.skillCd "+
              "INNER JOIN TechMatchSkillJenreCodeEntity tsjc ON tsc.skillJenreCd=tsjc.skillJenreCd "+
		      "INNER JOIN TechMatchSkillLevelEntity tsl ON  trs.skillLevelCd=tsl.skillLevelCd "+
              "WHERE tri.id=:id AND tsc.skillJenreCd=:skillType")
	public List<Object[]> getRequirementfrontEndSkills(@Param("id")Integer id,@Param("skillType")String skillType);


    /**
     * スキル情報を取得する親
     * @param id
     * @param skillType
     * @return
     */
    default List<TechMatchRequirementSkillResultEntity> getRequirementfrontEndSkillsFromRepository(Integer id,TechMatchSkillTypeEnum skillType) {
        return getRequirementfrontEndSkills(id,skillType.getJenreCd())
            .stream()
            .map(TechMatchRequirementSkillResultEntity::new)
            .collect(Collectors.toList());
    }
}
