package com.techmatch.base.logic.service;

import static com.techmatch.base.common.utility.TechMatchImageUtil.deleteFile;
import static com.techmatch.base.common.utility.TechMatchImageUtil.fileCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;
import com.techmatch.base.common.model.requirement.TechMatchListSearchModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementAndSkillsToApplyModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementSkillToApplyModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementToApplyModel;
import com.techmatch.base.common.model.user.TechMatchUserAllSkillModel;
import com.techmatch.base.common.model.user.TechMatchUserSkillModel;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.exception.TechMatchRequirementException;
import com.techmatch.base.exception.TechMatchValidationException;
import com.techmatch.base.logic.repository.TechMatchRequirementRepositoryExecution;
import com.techmatch.base.representative.service.IRequirementService;

@Service
public class TechMatchRequirementServiceImpl implements IRequirementService{
	@Autowired
	TechMatchRequirementRepositoryExecution techMatchRequirementRepositoryExecution;

	/**
	 * IDから募集情報を取得します
	 */
	@Override
	public TechMatchRequirementAndSkillsModel getRequirementAndSkill(Integer id) throws TechMatchException {
		if(id==null) {
			return null;
		}
    	return techMatchRequirementRepositoryExecution.getSavedRequirementAndSkillsModel(id);
	}

	/**
	 * 募集情報のリストを返します
	 */
	@Override
	public Page<TechMatchRequirementEntity> getRequirementList(TechMatchListSearchModel listSearchModel, Pageable pageable) throws TechMatchException {
		return techMatchRequirementRepositoryExecution.getRequirementList(listSearchModel, pageable);
	}
	
	/**
	 * 募集します
	 * @throws IOException
	 */
	@Override
	public TechMatchRequirementAndSkillsModel recruit(TechMatchRequirementAndSkillsToApplyModel requirementAndSKills) throws TechMatchException {
		TechMatchRequirementToApplyModel requirementModel = requirementAndSKills.getRequirementModel();
		if(requirementModel==null) {
			throw new TechMatchRequirementException("募集情報を入力してください");
		}
		// Validator を取得
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		// 募集基本情報のバリデーション
		Set<ConstraintViolation<TechMatchRequirementToApplyModel>> requirementResult = validator.validate(requirementModel);
		if(requirementResult.size()>0) {
			Map<String,String> errorMessages=new HashMap<>();
			for(ConstraintViolation<TechMatchRequirementToApplyModel> violation:requirementResult) {
				errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			throw new TechMatchValidationException(errorMessages);
		}

		// スキル情報があるか
		List<List<TechMatchRequirementSkillToApplyModel>> skillLists=new ArrayList<>();
		skillLists.add(requirementAndSKills.getFrontEndSkills());
		skillLists.add(requirementAndSKills.getBackEndSkills());
		skillLists.add(requirementAndSKills.getInfraSkills());
		skillLists.add(requirementAndSKills.getMlSkills());
		skillLists.add(requirementAndSKills.getNativeApplicationSkills());
		Set<ConstraintViolation<TechMatchRequirementSkillToApplyModel>> skillResult = null;
		for(List<TechMatchRequirementSkillToApplyModel> skillList:skillLists) {
			for(TechMatchRequirementSkillToApplyModel skillModel : skillList) {
				skillResult = validator.validate(skillModel);
			}
		}
		if(skillResult != null &&skillResult.size()>0) {
			Map<String,String> errorMessages=new HashMap<>();
			for(ConstraintViolation<TechMatchRequirementSkillToApplyModel> violation:skillResult) {
				errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			throw new TechMatchValidationException(errorMessages);
		}
		try {
			return  techMatchRequirementRepositoryExecution.recruit(requirementModel,skillLists);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 募集画像を登録します
	 */
	public void registerRequireImages(List<MultipartFile> images,int requirementId) throws TechMatchException{
		try {
			techMatchRequirementRepositoryExecution.registerRequireImages(images, requirementId);
		}catch(TechMatchException e) {
			throw e;
		}
	}


	/**
	 * 募集をキャンセルします
	 */
	@Override
	public void cancelRequirement(Integer id, String userId) throws TechMatchException {
		// id,userIdのnullチェック
		if(Objects.isNull(id) || Objects.isNull(userId)) {
			throw new TechMatchRequirementException("募集情報の指定または認証情報が不正です");
		}
		try {
			techMatchRequirementRepositoryExecution.cancelRequirement(id, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 募集を終了します
	 */
	@Override
	public void closeRequirement(Integer id, String userId) throws TechMatchException {
		// id,userIdのnullチェック
		if(Objects.isNull(id) || Objects.isNull(userId)) {
			throw new TechMatchRequirementException("募集情報の指定または認証情報が不正です");
		}
		try {
			techMatchRequirementRepositoryExecution.closeRequirement(id, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 募集を再開します
	 */
	@Override
	public void resumeRequirement(Integer id, String userId) throws TechMatchException {
		// id,userIdのnullチェック
		if(Objects.isNull(id) || Objects.isNull(userId)) {
			throw new TechMatchRequirementException("募集情報の指定または認証情報が不正です");
		}
		try {
			techMatchRequirementRepositoryExecution.resumeRequirement(id, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 応募者の情報を取得します
	 */
	@Override
	public List<TechMatchUserAllSkillModel> getApplicantUsersSkill(Integer id, String userId) throws TechMatchException {
		// id,userIdのnullチェック
		if(Objects.isNull(id) || Objects.isNull(userId)) {
			throw new TechMatchRequirementException("募集情報の指定または認証情報が不正です");
		}
		try {
			return techMatchRequirementRepositoryExecution.getApplicantUsersSkill(id, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 応募者を決定します
	 */
	@Override
	public void decideApplicant(TechMatchApplicationKey key, String requirerUserId) throws TechMatchException {
		if(Objects.isNull(key.getUserId()) || Objects.isNull(key.getId())) {
			throw new TechMatchRequirementException("応募者または募集を特定できませんでした");
		}
		techMatchRequirementRepositoryExecution.decideApplicant(key, requirerUserId);
	}

	/**
	 * 応募をキャンセルします
	 */
	@Override
	public void cancelApplication(TechMatchApplicationKey key, String requirerUserId) throws TechMatchException {
	}

}
