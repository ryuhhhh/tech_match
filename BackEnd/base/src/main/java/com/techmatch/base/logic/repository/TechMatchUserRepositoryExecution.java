package com.techmatch.base.logic.repository;

import static com.techmatch.base.common.utility.TechMatchConverter.entitySKill2modelUserSkill;
import static com.techmatch.base.common.utility.TechMatchConverter.entityUser2ResonseModelUser;
import static com.techmatch.base.common.utility.TechMatchConverter.frontModelUser2EntityUser;
import static com.techmatch.base.common.utility.TechMatchConverter.frontModelUser2UpdateEntityUser;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.techmatch.base.common.entity.skill.TechMatchUserSkillEntity;
import com.techmatch.base.common.entity.skill.TechMatchUserSkillKey;
import com.techmatch.base.common.entity.user.TechMatchUserEntity;
import com.techmatch.base.common.entity.user.TechMatchUserEntityForUpdate;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.user.TechMatchUserAllInfoModel;
import com.techmatch.base.common.model.user.TechMatchUserAllInfoToUpdateModel;
import com.techmatch.base.common.model.user.TechMatchUserAllSkillModel;
import com.techmatch.base.common.model.user.TechMatchUserFrontModel;
import com.techmatch.base.common.model.user.TechMatchUserSKillToUpdateModel;
import com.techmatch.base.common.model.user.TechMatchUserSkillModel;
import com.techmatch.base.common.utility.TechMatchSkillTypeEnum;
import com.techmatch.base.controller.rest_api.UserController;
import com.techmatch.base.exception.TechMatchAuthException;
import com.techmatch.base.exception.TechMatchDataException;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.representative.repository.natural.ITechMatchUserRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementInfoRepository;
import com.techmatch.base.representative.repository.skill.TechMatchSkillLevelRepository;
import com.techmatch.base.representative.repository.skill.TechMatchSkillRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserSkillRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserUpdateRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserDescribeSkillRepository;

@Service
public class TechMatchUserRepositoryExecution implements ITechMatchUserRepository{

	@Autowired
	TechMatchUserRepository techMatchUserRepository;
	@Autowired
	TechMatchUserDescribeSkillRepository techMatchUserDescribeSkillRepository;
    protected final static Logger logger = LoggerFactory.getLogger(TechMatchUserRepositoryExecution.class);

	/**
	 * ユーザの基本情報とスキル情報を取得する
	 */
	@Override
	public TechMatchUserAllInfoModel getUserAllInfo(String userId) throws TechMatchException {
		/**
		 * ユーザ情報を呼び出す
		 */
		TechMatchUserEntity userEntity;
		TechMatchUserFrontModel responseUserModel;
		try {
			userEntity = techMatchUserRepository.findById(userId).orElseThrow(()  -> new TechMatchDataException("ユーザ情報が読み取れませんでした"));
			responseUserModel = entityUser2ResonseModelUser(userEntity);
		}catch(TechMatchException e) {
			throw e;
		}
		/*
		 * スキル情報を呼び出す
		 */
		// ユーザのスキルの保存場所を用意する
		TechMatchUserAllSkillModel userAllSkillModel = new TechMatchUserAllSkillModel();
		List<TechMatchUserSkillModel> frontEndSkills;
		List<TechMatchUserSkillModel> backEndSkills;
		List<TechMatchUserSkillModel> infraSkills;
		List<TechMatchUserSkillModel> mlSkills;
		List<TechMatchUserSkillModel> nativeApplicationSkills;
		frontEndSkills = entitySKill2modelUserSkill(techMatchUserDescribeSkillRepository.getUserSKills(userId, TechMatchSkillTypeEnum.FRONT_END.getJenreCd()));
		backEndSkills = entitySKill2modelUserSkill(techMatchUserDescribeSkillRepository.getUserSKills(userId, TechMatchSkillTypeEnum.BACK_END.getJenreCd()));
		infraSkills = entitySKill2modelUserSkill(techMatchUserDescribeSkillRepository.getUserSKills(userId, TechMatchSkillTypeEnum.INFRASTRUCTURE.getJenreCd()));
		mlSkills = entitySKill2modelUserSkill(techMatchUserDescribeSkillRepository.getUserSKills(userId, TechMatchSkillTypeEnum.MACHINE_LEARNING.getJenreCd()));
		nativeApplicationSkills = entitySKill2modelUserSkill(techMatchUserDescribeSkillRepository.getUserSKills(userId, TechMatchSkillTypeEnum.NATIVE_APPLICATION.getJenreCd()));

		userAllSkillModel.setUserId(userId);
		userAllSkillModel.setFrontEndSkills(frontEndSkills);
		userAllSkillModel.setBackEndSkills(backEndSkills);
		userAllSkillModel.setInfraSkills(infraSkills);
		userAllSkillModel.setNativeApplicationSkills(nativeApplicationSkills);
		userAllSkillModel.setMlSkills(mlSkills);
		/*
		 * 基本情報とスキル情報を合わせる
		 */
		TechMatchUserAllInfoModel userAllInfoModel = new TechMatchUserAllInfoModel();
		userAllInfoModel.setTechMatchUserResponseModel(responseUserModel);
		userAllInfoModel.setTechMatchUserAllSkillModel(userAllSkillModel);
		return userAllInfoModel;
	}

	@Autowired
	MessageSource messageSource;
	@Autowired
	TechMatchSkillRepository techMatchSkillRepository;
	@Autowired
	TechMatchSkillLevelRepository techMatchSkillLevelRepository;
	@Autowired
	TechMatchUserSkillRepository techMatchUserSkillRepository;
	@Autowired
	TechMatchUserUpdateRepository techMatchUserUpdateRepository;
	

	/**
	 * ユーザ情報を更新する
	 */
	@Override
	public void updateUserAllInfo(
			TechMatchUserAllInfoToUpdateModel userAllInfoToUpdateModel) throws TechMatchException {
		TechMatchUserFrontModel frontModel = userAllInfoToUpdateModel.getUserFrontModel();
		// すでにアカウント情報が存在しているか調査
		/*List<String> errorMessages = new ArrayList<>();
		try {
			if(techMatchUserRepository.isFaceBookAccountAlreadyExisted(frontModel.getFacebookAccount()).size()>0) {
				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_FACEBOOK_ACCOUNT_EXISTED", null, null));
			}
			if(techMatchUserRepository.isTwitterAccountAlreadyExisted(frontModel.getTwitterAccount()).size()>0) {
				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_TWITTER_ACCOUNT_EXISTED", null, null));
			}
			if(techMatchUserRepository.isMailAddressAlreadyExisted(frontModel.getMailAddress()).size()>0) {
				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_MAIL_ADDRESS_EXISTED", null, null));
			}
			if(errorMessages.size()>0) {
				throw new TechMatchAuthException(errorMessages);
			}
		}catch(TechMatchAuthException e) {
			throw e;
		}*/
		/* ユーザ情報を更新する */
		// フロントユーザモデル -> ユーザエンティティ
		TechMatchUserEntityForUpdate entity = frontModelUser2UpdateEntityUser(frontModel);

		// 保存する
		techMatchUserUpdateRepository.save(entity);

		/*スキルの情報を更新する*/
		// スキルの存在確認
		for(TechMatchUserSKillToUpdateModel skill:userAllInfoToUpdateModel.getSkills()){
			// 存在するスキル情報か確認する
			techMatchSkillRepository.findById(skill.getSkillCd()).orElseThrow(()->new TechMatchDataException("スキル情報が存在しません。"));
			// 存在するスキルレベルか確認する
			techMatchSkillLevelRepository.findById(skill.getSkillLevelCd()).orElseThrow(()->new TechMatchDataException("スキルレベル情報が存在しません。"));
		}
		// スキル情報を一旦削除
		techMatchUserSkillRepository.deleteUserSKills(frontModel.getUserId());
		// スキル情報を挿入する
		for(TechMatchUserSKillToUpdateModel skill:userAllInfoToUpdateModel.getSkills()){
			// スキルエンティティを作る
			TechMatchUserSkillEntity skillEntity = new TechMatchUserSkillEntity(new TechMatchUserSkillKey(skill.getSkillCd(),frontModel.getUserId()),skill.getSkillLevelCd());
			// スキルを保存する
			techMatchUserSkillRepository.save(skillEntity);
		}
		logger.info("userId"+entity.getUserId()+"の情報を更新しました");
	}

	@Autowired
	TechMatchRequirementRepositoryExecution techMatchRequirementRepositoryExecution;
	@Autowired
	TechMatchRequirementInfoRepository techMatchRequirementInfoRepository;

	/**
	 * ユーザの募集情報を取得する
	 */
	@Override
	public List<TechMatchRequirementAndSkillsModel> getRequirementInfo(String userId) throws TechMatchException {
		return techMatchRequirementInfoRepository.getUsersRequirements(userId).stream().map((id)->techMatchRequirementRepositoryExecution.getSavedRequirementAndSkillsModel(id)).collect(Collectors.toList());
	}
}
