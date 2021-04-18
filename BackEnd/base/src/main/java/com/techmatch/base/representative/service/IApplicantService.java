package com.techmatch.base.representative.service;

import java.util.List;

import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.application.TechMatchBookmarksKey;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.exception.TechMatchException;

public interface IApplicantService {

	/**
	 * ブックマークします
	 */
	public void bookmark(TechMatchBookmarksKey bookmarkKey) throws TechMatchException;
	
	/**
	 * 応募します
	 * 
	 */
	public void apply(TechMatchApplicationKey applicationKey) throws TechMatchException;
	
	/**
	 * ブックーマーク情報を取得します
	 */
	List<TechMatchRequirementAndSkillsModel> techMatchBookmarksList(String userId) throws TechMatchException;
	
	/**
	 * 応募情報を返します
	 */
	List<TechMatchRequirementAndSkillsModel> getApplicationList(String userId) throws TechMatchException;

	/**
	 * ブックマークを解除します
	 */	
	public void cancelBookmark(TechMatchBookmarksKey bookmarkKey) throws TechMatchException;
	
	/**
	 * 応募を解除します
	 */
	public void cancelApplication(TechMatchApplicationKey applicationKey) throws TechMatchException;
	
	/**
	 * マッチ状態を確認します
	 */
	public boolean confirmIsMatched(String userId,Integer requirementId) throws TechMatchException;
}
