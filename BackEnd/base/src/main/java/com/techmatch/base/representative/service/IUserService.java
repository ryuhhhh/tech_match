package com.techmatch.base.representative.service;

import java.util.List;

import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.user.TechMatchUserAllInfoModel;
import com.techmatch.base.common.model.user.TechMatchUserAllInfoToUpdateModel;
import com.techmatch.base.exception.TechMatchException;

public interface IUserService {
	/**
	 * ユーザ情報を取得する
	 * @param userId
	 * @return
	 * @throws TechMatchException
	 */
	public TechMatchUserAllInfoModel getUserAllInfo(String userId) throws TechMatchException;
	
	/**
	 * ユーザの募集情報を取得する
	 */
	public List<TechMatchRequirementAndSkillsModel> getRequirementInfo(String userId) throws TechMatchException;
	
	/**
	 * ユーザの情報を更新する
	 */
	public void updateUserInfo(TechMatchUserAllInfoToUpdateModel userAllInfoToUpdateModel)throws TechMatchException;
	
	/**
	 * ユーザ情報を全削除する
	 * @param userId
	 * @throws TechMatchException
	 */
	public void deleteUserInfo(String userId) throws TechMatchException;
}
