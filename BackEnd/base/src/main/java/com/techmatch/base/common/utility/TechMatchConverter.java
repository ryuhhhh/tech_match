package com.techmatch.base.common.utility;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementListEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillResultEntity;
import com.techmatch.base.common.entity.user.TechMatchUserEntity;
import com.techmatch.base.common.entity.user.TechMatchUserEntityForUpdate;
import com.techmatch.base.common.entity.user.TechMatchTmpUserEntity;
import com.techmatch.base.common.entity.user.TechMatchUserDescribeSkillEntity;
import com.techmatch.base.common.model.requirement.TechMatchRequirementListModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementSkillModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementAndSkillsToApplyModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementToApplyModel;
import com.techmatch.base.common.model.user.TechMatchUserModel;
import com.techmatch.base.common.model.user.TechMatchUserFrontModel;
import com.techmatch.base.common.model.user.TechMatchUserSkillModel;

import io.jsonwebtoken.lang.Collections;


/**
 * 様々な情報の置換処理を行います
 * @author firmy
 *
 */
public class TechMatchConverter {
	public static java.sql.Date utilDate2SqlDate(java.util.Date date){
		if(date!=null) {
			String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
			java.sql.Date sqlDate = java.sql.Date.valueOf(str);
			return sqlDate;
		}
		return null;
	}

	public static String utilDate2StringDate(java.sql.Date date){
		if(date!=null) {
			String stringDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
			return stringDate;
		}
		return null;
	}

	public static String sqlTimestamp2StringDate(java.sql.Timestamp date){
		if(date!=null) {
			String stringTimestamp = new SimpleDateFormat("yyyy-MM-dd").format(date);
			return stringTimestamp;
		}
		return null;
	}

	public  static TechMatchUserEntity modelUser2EntityUser(TechMatchUserModel modelUser) {
		TechMatchUserEntity  entityUser = new TechMatchUserEntity();
		entityUser.setUserId(modelUser.getUserId());
		entityUser.setPassword(modelUser.getPassword());
		entityUser.setMailAddress(modelUser.getMailAddress());
		entityUser.setTwitterAccount(modelUser.getTwitterAccount());
		entityUser.setFacebookAccount(modelUser.getFacebookAccount());
		entityUser.setIntroduce(modelUser.getIntroduce());
		entityUser.setUserImage(modelUser.getUserImage());
		entityUser.setCreateDate(utilDate2SqlDate(modelUser.getCreateDate()));
		entityUser.setUpdateDate(utilDate2SqlDate(modelUser.getUpdateDate()));
		return entityUser;
	}

	/**
	 * ユーザモデルから仮登録ユーザエンティティへ
	 * @param modelUser
	 * @return
	 */
	public  static TechMatchTmpUserEntity modelUser2TmpEntityUser(TechMatchUserModel modelUser) {
		TechMatchTmpUserEntity  entityUser = new TechMatchTmpUserEntity();
		entityUser.setUserId(modelUser.getUserId());
		entityUser.setPassword(modelUser.getPassword());
		entityUser.setMailAddress(modelUser.getMailAddress());
		entityUser.setCreateDate(utilDate2SqlDate(modelUser.getCreateDate()));
		entityUser.setUpdateDate(utilDate2SqlDate(modelUser.getUpdateDate()));
		return entityUser;
	}
	

	/**
	 * 仮登録ユーザエンティティから本登録ユーザエンティティへ
	 * @param modelUser
	 * @return
	 */
	public  static TechMatchUserEntity TmpEntityUser2EntityUser(TechMatchTmpUserEntity tmpEntityUser) {
		TechMatchUserEntity  entityUser = new TechMatchUserEntity();
		entityUser.setUserId(tmpEntityUser.getUserId());
		entityUser.setPassword(tmpEntityUser.getPassword());
		entityUser.setMailAddress(tmpEntityUser.getMailAddress());
//		entityUser.setCreateDate(utilDate2SqlDate(tmpEntityUser.getCreateDate()));
//		entityUser.setUpdateDate(utilDate2SqlDate(tmpEntityUser.getUpdateDate()));
		return entityUser;
	}
	
		
	
	/**
	 * 更新用モデルからUSERエンティティへ
	 * @param modelUser
	 * @return
	 */
	public  static TechMatchUserEntity frontModelUser2EntityUser(TechMatchUserFrontModel modelUser) {
		TechMatchUserEntity  entityUser = new TechMatchUserEntity();
		entityUser.setUserId(modelUser.getUserId());
		entityUser.setMailAddress(modelUser.getMailAddress());
		entityUser.setTwitterAccount(modelUser.getTwitterAccount());
		entityUser.setFacebookAccount(modelUser.getFacebookAccount());
		entityUser.setIntroduce(modelUser.getIntroduce());
		entityUser.setUserImage(modelUser.getUserImage());
		return entityUser;
	}

	/**
	 * 更新用モデルから更新用USERエンティティへ
	 * @param modelUser
	 * @return
	 */
	public  static TechMatchUserEntityForUpdate frontModelUser2UpdateEntityUser(TechMatchUserFrontModel modelUser) {
		TechMatchUserEntityForUpdate  entityUser = new TechMatchUserEntityForUpdate();
		entityUser.setUserId(modelUser.getUserId());
//		entityUser.setMailAddress(modelUser.getMailAddress());
		entityUser.setTwitterAccount(modelUser.getTwitterAccount());
		entityUser.setFacebookAccount(modelUser.getFacebookAccount());
		entityUser.setIntroduce(modelUser.getIntroduce());
//		entityUser.setUserImage(modelUser.getUserImage());
		return entityUser;
	}
	
	/**
	 * ユーザエンティティからユーザモデルに変換
	 * @param entityUser
	 * @return
	 */
	public  static TechMatchUserFrontModel entityUser2ResonseModelUser(TechMatchUserEntity entityUser) {
		TechMatchUserFrontModel  responseModelUser = new TechMatchUserFrontModel();
		responseModelUser.setUserId(entityUser.getUserId());
		responseModelUser.setMailAddress(entityUser.getMailAddress());
		responseModelUser.setTwitterAccount(entityUser.getTwitterAccount());
		responseModelUser.setFacebookAccount(entityUser.getFacebookAccount());
		responseModelUser.setIntroduce(entityUser.getIntroduce());
		responseModelUser.setUserImage(entityUser.getUserImage());
		responseModelUser.setCreateDate(utilDate2SqlDate(entityUser.getCreateDate()));
		responseModelUser.setUpdateDate(utilDate2SqlDate(entityUser.getUpdateDate()));
		return responseModelUser;
	}

	public static List<TechMatchRequirementSkillModel> entitySKill2modelSkill (List<TechMatchRequirementSkillResultEntity> skillEntityList){
		return skillEntityList.stream().map(TechMatchRequirementSkillModel::new).collect(Collectors.toList());
	}

	public static List<TechMatchUserSkillModel> entitySKill2modelUserSkill (List<TechMatchUserDescribeSkillEntity> skillEntityList){
		return skillEntityList.stream().map(TechMatchUserSkillModel::new).collect(Collectors.toList());
	}
	/**
	 * 募集情報リストエンティティから募集情報リストモデルに変換する
	 * @param entity
	 * @return
	 */
	public static TechMatchRequirementListModel requirementEntity2requirementModel(TechMatchRequirementListEntity entity) {
		TechMatchRequirementListModel model = new TechMatchRequirementListModel();
		model.setId(entity.getId());
		model.setUserId(entity.getUserId());
		model.setTitle(entity.getTitle());
		model.setExpireDate(utilDate2StringDate(entity.getExpireDate()));
		return model;
	}

	/**
	 * 募集情報エンティティから募集情報モデルに変換する
	 * @param entity
	 * @return
	 */
	public static TechMatchRequirementModel requirementEntity2requirementModel(TechMatchRequirementEntity entity) {
		TechMatchRequirementModel model = new TechMatchRequirementModel();
		model.setId(entity.getId());
		model.setUserId(entity.getUserId());
		model.setTitle(entity.getTitle());
		model.setWhyText(entity.getWhyText());
		model.setWhatText(entity.getWhatText());
		model.setHowText(entity.getHowText());
		model.setImage_1(entity.getImage_1());
		model.setImage_2(entity.getImage_2());
		model.setRequirementNum(entity.getRequirementNum());
		model.setTargetDate(utilDate2StringDate(entity.getTargetDate()));
		model.setExpireDate(utilDate2StringDate(entity.getExpireDate()));
		model.setCreateDate(sqlTimestamp2StringDate(entity.getCreateDate()));
		model.setUpdateDate(sqlTimestamp2StringDate(entity.getUpdateDate()));
		return model;
	}

	/**
	 * 募集情報モデルから募集情報エンティティに変換する
	 *
	 */
	public static TechMatchRequirementEntity toApplyRequirementModel2toApplyRequirementEntity(TechMatchRequirementToApplyModel model) {
		TechMatchRequirementEntity entity = new TechMatchRequirementEntity();
		entity.setUserId(model.getUserId());
		entity.setTitle(model.getTitle());
		entity.setWhyText(model.getWhyText());
		entity.setWhatText(model.getWhatText());
		entity.setHowText(model.getHowText());
		entity.setRequirementNum(model.getRequiredNum());
		entity.setImage_1(model.getImage_1());
		entity.setImage_2(model.getImage_2());
		entity.setExpireDate(utilDate2SqlDate(model.getExpireDate()));
		entity.setTargetDate(utilDate2SqlDate(model.getTargetDate()));
		return entity;
	}
}
