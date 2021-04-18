package com.techmatch.base.controller.rest_api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;
import com.techmatch.base.common.model.requirement.TechMatchListSearchModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsListModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementAndSkillsToApplyModel;
import com.techmatch.base.common.model.user.TechMatchUserAllSkillModel;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.logic.service.TechMatchRequirementServiceImpl;

@RestController
@RequestMapping("/api/requirement")
public class RequirementController {

	/**
	 * IDから募集情報を取得します
	 * @param 募集id
	 * @return
	 * @throws TechMatchException
	 */
	// TODO entityではなくモデルを返す
	@PostMapping("/get/{id}")
	@ResponseStatus(HttpStatus.OK)
	public  TechMatchRequirementAndSkillsModel getRequirementAndSkill(@PathVariable("id") Integer id) throws TechMatchException {
		try {
			return techMatchRequirementServiceImpl.getRequirementAndSkill(id);
		}catch(TechMatchException e) {
			throw e;
		}
	}
	
	/**
	 * リストを返します
	 * 最後
	 * @param fromNum toNum フィルタ情報(スキル - レベル,全文検索,
	 * @return
	 * @throws TechMatchException
	 */
	// TODO entityではなくモデルを返す
	@PostMapping("/getList")
	@ResponseStatus(HttpStatus.OK)
	public Page<TechMatchRequirementEntity> getRequirementAndSkillsListModel(@RequestBody TechMatchListSearchModel listSearchModel,Pageable pageable) throws TechMatchException {
		try {
			return techMatchRequirementServiceImpl.getRequirementList(listSearchModel, pageable);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	@Autowired
	TechMatchRequirementServiceImpl techMatchRequirementServiceImpl;

	/**
	 * 募集する
	 * @param requirementAndSKills
	 * @return
	 * @throws TechMatchException
	 */
	@PostMapping("/require")
	@ResponseStatus(HttpStatus.OK)
	public TechMatchRequirementAndSkillsModel recruit(@RequestBody TechMatchRequirementAndSkillsToApplyModel requirementAndSKills) throws TechMatchException{
		// requirementAndSKillsにuserIdを設定する
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		requirementAndSKills.getRequirementModel().setUserId(userId);
		try {
			// サービスを呼び出し、登録処理を実行する
			return techMatchRequirementServiceImpl.recruit(requirementAndSKills);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 募集画像を登録する
	 */
	@PostMapping("/require-images")
	@ResponseStatus(HttpStatus.OK)
	public void registerRequireImages(@RequestParam List<MultipartFile> images,@RequestParam Integer requirementId) throws TechMatchException{
		try {
			// サービスを呼び出し、登録処理を実行する
			techMatchRequirementServiceImpl.registerRequireImages(images,(int)requirementId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 募集をキャンセルする
	 * @param id
	 * @throws TechMatchException
	 */
	@PostMapping("/cancel")
	@ResponseStatus(HttpStatus.OK)
	public void cancelRequirement(@RequestBody Integer id) throws TechMatchException{
		// requirementAndSKillsにuserIdを設定する
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		try {
			// サービスを呼び出し、登録処理を実行する
			techMatchRequirementServiceImpl.cancelRequirement(id, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 募集を終了する
	 * @param id
	 * @throws TechMatchException
	 */
	@PostMapping("/close")
	@ResponseStatus(HttpStatus.OK)
	public void closeRequirement(@RequestBody Integer id) throws TechMatchException{
		// requirementAndSKillsにuserIdを設定する
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		try {
			// サービスを呼び出し、登録処理を実行する
			techMatchRequirementServiceImpl.closeRequirement(id, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 募集終了を解除する
	 * @param id
	 * @throws TechMatchException
	 */
	@PostMapping("/resume")
	@ResponseStatus(HttpStatus.OK)
	public void resumeRequirement(@RequestBody Integer id) throws TechMatchException{
		// requirementAndSKillsにuserIdを設定する
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		try {
			// サービスを呼び出し、登録処理を実行する
			techMatchRequirementServiceImpl.resumeRequirement(id, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 応募者の情報を取得します
	 */
	@PostMapping("/applicant")
	@ResponseStatus(HttpStatus.OK)
	public List<TechMatchUserAllSkillModel> getApplicantUsersSkill(@RequestBody Integer id) throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		try {
			// サービスを呼び出し、検索処理を実行する
			return techMatchRequirementServiceImpl.getApplicantUsersSkill(id, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 応募者を決定します
	 */
	@PostMapping("/decide-applicant")
	@ResponseStatus(HttpStatus.OK)
	public void decideApplicant(@RequestBody TechMatchApplicationKey applicationKey) throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		try {
			// サービスを呼び出し、検索処理を実行する
			techMatchRequirementServiceImpl.decideApplicant(applicationKey, userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}
}
