package com.techmatch.base.representative.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;
import com.techmatch.base.common.model.requirement.TechMatchListSearchModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementAndSkillsToApplyModel;
import com.techmatch.base.common.model.user.TechMatchUserAllSkillModel;
import com.techmatch.base.common.model.user.TechMatchUserSkillModel;
import com.techmatch.base.exception.TechMatchException;

public interface IRequirementService {
	/**
	 * 募集情報を登録します
	 * @return 登録結果
	 * @throws IOException 
	 */
	public TechMatchRequirementAndSkillsModel recruit(TechMatchRequirementAndSkillsToApplyModel requirementAndSKills) throws TechMatchException, IOException;

	/**
	 * 募集を終了します
	 * @param requirement_id
	 * @param user_id
	 */
	public void closeRequirement(Integer id,String userId) throws TechMatchException;
	/**
	 * 募集を取り止めます
	 * @param requirement_id
	 * @param user_id
	 */
	public void cancelRequirement(Integer id,String userId) throws TechMatchException;

	/**
	 * 募集を再開します
	 * @param requirement_id
	 * @param user_id
	 */
	public void resumeRequirement(Integer id,String userId) throws TechMatchException;

	/**
	 * 応募者の情報を取得します
	 * ユーザID・スキル・スキルレベル
	 * @param userId
	 * @param id
	 */
	public List<TechMatchUserAllSkillModel> getApplicantUsersSkill(Integer id,String requirerId) throws TechMatchException;

	/**
	 * 応募者の決定
	 * @param TechMatchApplicationKey
	 * @param requirerUserId
	 */
	public void decideApplicant(TechMatchApplicationKey key,String requirerUserId) throws TechMatchException;

	/**
	 * 応募者決定の解除
	 * @param TechMatchApplicationKey
	 * @param requirerUserId
	 */
	public void cancelApplication(TechMatchApplicationKey key,String requirerUserId) throws TechMatchException;

	/**
	 * 募集情報のリストを返します
	 */
	public Page<TechMatchRequirementEntity> getRequirementList(TechMatchListSearchModel listSearchModel, Pageable pageable) throws TechMatchException;

	/**
	 * IDから募集情報を取得します
	 */
	public  TechMatchRequirementAndSkillsModel getRequirementAndSkill(Integer id) throws TechMatchException;
}
