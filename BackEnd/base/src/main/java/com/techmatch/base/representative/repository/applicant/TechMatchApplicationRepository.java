package com.techmatch.base.representative.repository.applicant;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.application.TechMatchApplicationEntity;
import com.techmatch.base.common.entity.application.TechMatchApplicationKey;

@Repository
public interface TechMatchApplicationRepository extends JpaRepository<TechMatchApplicationEntity, TechMatchApplicationKey>  {
	@Query("SELECT a FROM TechMatchApplicationEntity a WHERE a.applicationKey.userId = :user_id")
	public List<TechMatchApplicationEntity> getApplications(@Param("user_id")String userId);

	/**
	 * 応募情報をユーザIDより削除します
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM TechMatchApplicationEntity a WHERE a.applicationKey.userId = :user_id")
	public void deleteUserApplications(@Param("user_id")String userId);

	/**
	 * 応募しているユーザIDを取得します
	 * @param userId
	 * @return
	 */
	@Query("SELECT a FROM TechMatchApplicationEntity a WHERE a.applicationKey.id = :id")
	public List<TechMatchApplicationEntity> getApplicantSkill(@Param("id")Integer id);

	/**
	 * 応募者を決定します
	 * @param applicationKey
	 * @param requirerUserId
	 */
	@Transactional
	@Modifying
	@Query(value="UPDATE TechMatchApplicationEntity ae SET ae.isAdoption = true "+
			"WHERE ae.applicationKey=:application_key AND "+
			"ae.applicationKey.id =  (SELECT re.id FROM TechMatchRequirementEntity re "+
			"WHERE re.userId = :requirer_id AND re.id = :requirement_id)")
	public void decideApplicant(@Param("application_key")TechMatchApplicationKey applicationKey,@Param("requirement_id")Integer id,@Param("requirer_id")String requirerUserId);

	/**
	 * 決定済みかどうか確認します
	 */
	@Query("SELECT a.isAdoption FROM TechMatchApplicationEntity a WHERE a.applicationKey.id = :requirementId AND a.applicationKey.userId = :userId")
	public Boolean confirmIsMatched(@Param("userId")String userId,@Param("requirementId")Integer requirementId);
	

	/**
	 * ユーザIDより募集している情報に対する応募を全て削除します
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM TechMatchApplicationEntity ta WHERE ta.applicationKey.id "
			+ "IN ( SELECT tre.id FROM TechMatchRequirementEntity tre WHERE tre.userId = :userId )")
	public void deleteApplicationsByUsersRequirementId(@Param("userId")String userId);

	/**
	 * ユーザIDより募集情報に紐づく募集スキル情報を全て削除します
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM TechMatchRequirementSkillEntity trs WHERE trs.id "
			+ "IN ( SELECT tre.id FROM TechMatchRequirementEntity tre WHERE tre.userId = :userId )")
	public void deleteRequirementSkillsByUsersRequirementId(@Param("userId")String userId);
}
