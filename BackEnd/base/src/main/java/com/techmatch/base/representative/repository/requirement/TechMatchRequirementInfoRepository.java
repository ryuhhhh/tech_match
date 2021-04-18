package com.techmatch.base.representative.repository.requirement;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.application.TechMatchBookmarksEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;
import com.techmatch.base.common.model.application.TechMatchBookmarksModel;
@Repository
public interface TechMatchRequirementInfoRepository extends JpaRepository<TechMatchRequirementEntity, Integer>,JpaSpecificationExecutor<TechMatchRequirementEntity>{
	/**
	 * 募集状態を変更する
	 */
	@Transactional
	@Modifying
	@Query("UPDATE TechMatchRequirementEntity re SET re.requirementStatusCd = :status_cd, re.updateDate = CURRENT_TIMESTAMP WHERE re.userId = :user_id AND re.id = :requirement_id")
	public void changeRequirementStatus(@Param("requirement_id")Integer id, @Param("user_id")String userId,@Param("status_cd")String statusCd);

	/**
	 * ユーザの募集情報を取得する
	 */
	@Query("SELECT re.id FROM TechMatchRequirementEntity re  WHERE re.userId = :user_id ")
	public List<Integer> getUsersRequirements( @Param("user_id")String userId);

	/**
	 * 募集状態を削除する
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM TechMatchRequirementEntity re WHERE re.userId = :user_id ")
	public void deleteUserRequirements(@Param("user_id")String userId);
	
	/**
	 * 	画像情報を更新する x2
	 */
	@Transactional
	@Modifying
	@Query("UPDATE TechMatchRequirementEntity re SET re.image_1 = :image_1, re.image_2 = :image_2  WHERE re.id = :requirement_id")
	public void updateRequirementImagesInfo(@Param("requirement_id")Integer id,@Param("image_1")String image1,@Param("image_2")String image2);
	
	/**
	 * 	画像情報を更新する x1
	 */
	@Transactional
	@Modifying
	@Query("UPDATE TechMatchRequirementEntity re SET re.image_1 = :image_1 WHERE re.id = :requirement_id")
	public void updateRequirementImageInfo(@Param("requirement_id")Integer id,@Param("image_1")String image1);

}
