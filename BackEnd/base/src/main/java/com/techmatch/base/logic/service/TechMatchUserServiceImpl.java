package com.techmatch.base.logic.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.user.TechMatchUserAllInfoModel;
import com.techmatch.base.common.model.user.TechMatchUserAllInfoToUpdateModel;
import com.techmatch.base.common.model.user.TechMatchUserFrontModel;
import com.techmatch.base.common.model.user.TechMatchUserModel;
import com.techmatch.base.common.model.user.TechMatchUserSKillToUpdateModel;
import com.techmatch.base.exception.TechMatchAuthException;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.exception.TechMatchValidationException;
import com.techmatch.base.logic.repository.TechMatchUserRepositoryExecution;
import com.techmatch.base.representative.repository.applicant.TechMatchApplicationRepository;
import com.techmatch.base.representative.repository.applicant.TechMatchBookmarksRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementInfoRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserSkillRepository;
import com.techmatch.base.representative.service.IUserService;

@Service
public class TechMatchUserServiceImpl implements IUserService{
	@Autowired
	MessageSource messageSource;
	@Autowired
	TechMatchUserRepositoryExecution techMatchUserRepositoryExecution;
    protected final static Logger logger = LoggerFactory.getLogger(TechMatchUserRepositoryExecution.class);

	/*
	 * ユーザの情報を取得する
	 */
	@Override
	public TechMatchUserAllInfoModel getUserAllInfo(String userId) throws TechMatchException {
		/**
		 * バリデーション
		 */
		if(Objects.isNull(userId) || userId.length() <=0) {
			throw new TechMatchValidationException(messageSource.getMessage("BASE.AUTH.CANT_READ_USERID",null,null));
		}
		try {
			return techMatchUserRepositoryExecution.getUserAllInfo(userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * ユーザの情報を更新する
	 */
	@Override
	public void updateUserInfo(TechMatchUserAllInfoToUpdateModel userAllInfoToUpdateModel) throws TechMatchException{
		/*
		 * バリデーションを実施する
		 */
		// Validator を取得
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		// ユーザの情報をバリデーション
		Set<ConstraintViolation<TechMatchUserFrontModel>> result = validator.validate(userAllInfoToUpdateModel.getUserFrontModel());
		if(result.size()>0) {
			Map<String,String> errorMessages=new HashMap<>();
			for(ConstraintViolation<TechMatchUserFrontModel> violation:result) {
				errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			throw new TechMatchValidationException(errorMessages);
		}
		// スキル情報のバリデーション
		for(TechMatchUserSKillToUpdateModel skill:userAllInfoToUpdateModel.getSkills()) {
			try {
				Set<ConstraintViolation<TechMatchUserSKillToUpdateModel>> skillValidationResult = validator.validate(skill);
				if(skillValidationResult.size()>0) {
					Map<String,String> errorMessages=new HashMap<>();
					for(ConstraintViolation<TechMatchUserSKillToUpdateModel> violation:skillValidationResult) {
						errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
					}
					throw new TechMatchValidationException(errorMessages);
				}
			}catch(TechMatchValidationException e) {
				throw e;
			}
		}
		// 更新処理を行う
		techMatchUserRepositoryExecution.updateUserAllInfo(userAllInfoToUpdateModel);
	}

	/**
	 * ユーザの募集情報を取得する
	 */
	@Override
	public List<TechMatchRequirementAndSkillsModel> getRequirementInfo(String userId) throws TechMatchException {
		if(Objects.isNull(userId) || userId.length() <=0) {
			throw new TechMatchValidationException(messageSource.getMessage("BASE.AUTH.CANT_READ_USERID",null,null));
		}
		try {
			return techMatchUserRepositoryExecution.getRequirementInfo(userId);	
		}catch(TechMatchException e) {
			throw e;
		}
	}

	@Autowired
	TechMatchBookmarksRepository techMatchBookmarksRepository;
	@Autowired 
	TechMatchApplicationRepository techMatchApplicationRepository;
	@Autowired
	TechMatchUserSkillRepository techMatchUserSkillRepository;
	@Autowired
	TechMatchRequirementInfoRepository techMatchRequirementInfoRepository;
	@Autowired
	TechMatchUserRepository techMatchUserRepository;
	@Override
	public void deleteUserInfo(String userId) throws TechMatchException {
		/*
		 * レポジトリを呼び出し削除を行う
		 */
		try {
			// ブックマーク
			techMatchBookmarksRepository.deleteUserBookmarks(userId);
			// 応募情報 
			techMatchApplicationRepository.deleteUserApplications(userId);
			// 他ユーザからの応募を削除
			techMatchApplicationRepository.deleteApplicationsByUsersRequirementId(userId);
			// 募集情報に紐づくをスキル情報を削除
			techMatchApplicationRepository.deleteRequirementSkillsByUsersRequirementId(userId);
			// 募集情報 
			techMatchRequirementInfoRepository.deleteUserRequirements(userId);
			// スキル情報
			techMatchUserSkillRepository.deleteUserSKills(userId);
			// 基本情報
			techMatchUserRepository.deleteById(userId);
		} catch(Exception e) {
			logger.error(userId+ "退会処理に失敗しました");
			throw new TechMatchAuthException("退会処理に失敗しました。お問い合わせください。");
		}
	}
}
