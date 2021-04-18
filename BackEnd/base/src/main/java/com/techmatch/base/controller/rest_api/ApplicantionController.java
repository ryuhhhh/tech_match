package com.techmatch.base.controller.rest_api;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.application.TechMatchBookmarksEntity;
import com.techmatch.base.common.entity.application.TechMatchBookmarksKey;
import com.techmatch.base.common.model.application.TechMatchBookmarksModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.logic.service.TechMatchApplicantServiceImpl;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantionController {

	@Autowired
	TechMatchApplicantServiceImpl techMatchApplicantServiceImpl;
	/**
	 * 認証情報よりユーザIDを取得し、ブックマークを返します。
	 * @param なし
	 * @return
	 * @throws TechMatchException
	 */
	@GetMapping("/bookmarks")
	@ResponseStatus(HttpStatus.OK)
	public List<TechMatchRequirementAndSkillsModel> getBookMarks() throws TechMatchException {
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		List<TechMatchRequirementAndSkillsModel> requirementAndSkillsModelList;
		String userId = auth.getName();
		try {
			// 情報取得
			requirementAndSkillsModelList = techMatchApplicantServiceImpl.techMatchBookmarksList(userId);
		}catch(TechMatchException e) {
			throw e;
		}
		// ブックマーク情報を返す
		return requirementAndSkillsModelList;
	}


	/**
	 * ユーザの応募情報を返す
	 * @param なし
	 * @return 応募情報一覧
	 */
	@GetMapping("/list")
	@ResponseStatus(HttpStatus.OK)
	 public List<TechMatchRequirementAndSkillsModel> getList()  throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		List<TechMatchRequirementAndSkillsModel> requirementAndSkillsModelList;
		String userId = auth.getName();
		try {
			requirementAndSkillsModelList = techMatchApplicantServiceImpl.getApplicationList(userId);
			// マッチしたかどうか取得する
			for(TechMatchRequirementAndSkillsModel requirementAndSkillsModel:requirementAndSkillsModelList) {
				requirementAndSkillsModel.setAdoption(confirmIsMatched(requirementAndSkillsModel.getRequirementModel().getId()));
			}
		}catch(TechMatchException e) {
			throw e;
		}
		// 応募情報を返す
		return requirementAndSkillsModelList;
	}

	/**
	 * ブックマークを解除する
	 * @param ブックマークした募集のID
	 * @throws TechMatchException
	 */
	@PostMapping("/cancel/bookmark")
	@ResponseStatus(HttpStatus.OK)
	public void cancelBookmark(@RequestBody Integer id) throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			TechMatchBookmarksKey bookmarksKey = new TechMatchBookmarksKey(id,auth.getName());
			techMatchApplicantServiceImpl.cancelBookmark(bookmarksKey);
		} catch(TechMatchException e) {
			throw e;
		}
	}
	/**
	 * 応募を解除する
	 * @param 応募した募集のID
	 * @throws TechMatchException
	 */
	@PostMapping("/cancel/application")
	@ResponseStatus(HttpStatus.OK)
	public void cancelApplication(@RequestBody Integer id) throws TechMatchException {
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			TechMatchApplicationKey applicationKey = new TechMatchApplicationKey(id,auth.getName());
			techMatchApplicantServiceImpl.cancelApplication(applicationKey);
		} catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * ブックマークする
	 * @param ブックマークする募集のID
	 */
	@PostMapping("/bookmark")
	@ResponseStatus(HttpStatus.OK)
	public void bookmark(@RequestBody Integer id) throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			TechMatchBookmarksKey bookmarksKey = new TechMatchBookmarksKey(id,auth.getName());
			techMatchApplicantServiceImpl.bookmark(bookmarksKey);
		} catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 応募する
	 * @param 応募する募集のID
	 * @throws TechMatchException
	 */
	@PostMapping("/application")
	@ResponseStatus(HttpStatus.OK)
	public void apply(@RequestBody Integer id) throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			TechMatchApplicationKey applicationKey = new TechMatchApplicationKey(id,auth.getName());
			techMatchApplicantServiceImpl.apply(applicationKey);
		} catch(TechMatchException e) {
			throw e;
		}
	}
	
	/**
	 * 自身が応募に対してマッチしているかどうか確認する
	 */
	@PostMapping("/confirm-is-matched")
	@ResponseStatus(HttpStatus.OK)
	public boolean confirmIsMatched(@RequestBody Integer requirementId) throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		// ログイン前はfalseを返す
		if(Objects.isNull(auth)) {
			return false;
		}
		try {
			return techMatchApplicantServiceImpl.confirmIsMatched(auth.getName(),requirementId);
		} catch(TechMatchException e) {
			throw e;
		}
	}
}
