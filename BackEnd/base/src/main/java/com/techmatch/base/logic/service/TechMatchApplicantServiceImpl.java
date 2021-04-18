package com.techmatch.base.logic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmatch.base.common.entity.application.TechMatchApplicationEntity;
import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.application.TechMatchBookmarksEntity;
import com.techmatch.base.common.entity.application.TechMatchBookmarksKey;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillResultEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementStatusEntity;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementSkillModel;
import com.techmatch.base.common.utility.TechMatchSkillTypeEnum;
import com.techmatch.base.controller.rest_api.MasterController;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.exception.TechMatchRequirementException;
import com.techmatch.base.representative.repository.applicant.TechMatchApplicationRepository;
import com.techmatch.base.representative.repository.applicant.TechMatchBookmarksRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementInfoRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementSkillResultRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementStatusRepository;
import com.techmatch.base.representative.service.IApplicantService;

import static com.techmatch.base.common.utility.TechMatchConverter.entitySKill2modelSkill;
import static com.techmatch.base.common.utility.TechMatchConverter.requirementEntity2requirementModel;
@Service
public class TechMatchApplicantServiceImpl implements IApplicantService{
	@Autowired
	TechMatchBookmarksRepository techMatchBookmarksRepository;
	@Autowired
	TechMatchRequirementInfoRepository techMatchRequirementInfoRepository;
	@Autowired
	TechMatchRequirementStatusRepository techMatchRequirementStatusRepository;
	@Autowired
	TechMatchRequirementSkillResultRepository techMatchSkillRepository;

    protected final static Logger logger = LoggerFactory.getLogger(TechMatchApplicantServiceImpl.class);

	@Override
	public List<TechMatchRequirementAndSkillsModel> techMatchBookmarksList(String userId) throws TechMatchException {
		// userIdを元にブックマークしたID情報を取得する
		List<TechMatchBookmarksEntity> bookmarksList = techMatchBookmarksRepository.getBookmarks(userId);

		List<TechMatchRequirementAndSkillsModel> requirementAndSkillsModelsList=new ArrayList<>();
		// ループ
		for(TechMatchBookmarksEntity bookmark:bookmarksList) {
			TechMatchRequirementAndSkillsModel requirementAndSkillsModel = new TechMatchRequirementAndSkillsModel();
			TechMatchRequirementModel techMatchRequirementModel = new TechMatchRequirementModel();
			List<TechMatchRequirementSkillModel> frontEndSkills;
			List<TechMatchRequirementSkillModel> backEndSkills;
			List<TechMatchRequirementSkillModel> infraSkills;
			List<TechMatchRequirementSkillModel> mlSkills;
			List<TechMatchRequirementSkillModel> nativeApplicationSkills;
			//IDを取得する
			int id = bookmark.getBookmarksKey().getId();
			// ブックマークしたID情報より募集情報を取得する
			TechMatchRequirementEntity requirementEntity= techMatchRequirementInfoRepository.findById(id).get();
			techMatchRequirementModel = requirementEntity2requirementModel(requirementEntity);
			// 募集情報のステータスを取得する
			TechMatchRequirementStatusEntity requirementStatus = techMatchRequirementStatusRepository.findById(requirementEntity.getRequirementStatusCd()).get();
			techMatchRequirementModel.setRequirementStatus(requirementStatus.getName());
			// ブックマークしたID情報より各スキル情報を取得する
			List<TechMatchRequirementSkillResultEntity> frontEndSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.FRONT_END);
			List<TechMatchRequirementSkillResultEntity> backEndSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.BACK_END);
			List<TechMatchRequirementSkillResultEntity> infraSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.INFRASTRUCTURE);
			List<TechMatchRequirementSkillResultEntity> mlSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.MACHINE_LEARNING);
			List<TechMatchRequirementSkillResultEntity> nativeApplicationSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.NATIVE_APPLICATION);
			// 取得したスキル情報をエンティティからモデルに変換する
			frontEndSkills =  entitySKill2modelSkill(frontEndSkillEntities);
			backEndSkills = entitySKill2modelSkill(backEndSkillEntities);
			infraSkills = entitySKill2modelSkill(infraSkillEntities);
			mlSkills = entitySKill2modelSkill(mlSkillEntities);
			nativeApplicationSkills = entitySKill2modelSkill(nativeApplicationSkillEntities);
			// それぞれをTechMatchRequirementAndSkillsModelにいれる
			requirementAndSkillsModel.setRequirementModel(techMatchRequirementModel);
			requirementAndSkillsModel.setFrontEndSkills(frontEndSkills);
			requirementAndSkillsModel.setBackEndSkills(backEndSkills);
			requirementAndSkillsModel.setInfraSkills(infraSkills);
			requirementAndSkillsModel.setMlSkills(mlSkills);
			requirementAndSkillsModel.setNativeApplicationSkills(nativeApplicationSkills);
			// Listに追加する
			requirementAndSkillsModelsList.add(requirementAndSkillsModel);
		}
		return requirementAndSkillsModelsList;
	}

	@Autowired
	TechMatchApplicationRepository techMatchApplicationRepository;
	@Override
	public List<TechMatchRequirementAndSkillsModel> getApplicationList(String userId) throws TechMatchException {
		// userIdを元に応募したID情報を取得する
		List<TechMatchApplicationEntity> bookmarksList = techMatchApplicationRepository.getApplications(userId);
		List<TechMatchRequirementAndSkillsModel> requirementAndSkillsModelsList=new ArrayList<>();
		// ループ
		for(TechMatchApplicationEntity bookmark:bookmarksList) {
			TechMatchRequirementAndSkillsModel requirementAndSkillsModel = new TechMatchRequirementAndSkillsModel();
			TechMatchRequirementModel techMatchRequirementModel = new TechMatchRequirementModel();
			List<TechMatchRequirementSkillModel> frontEndSkills;
			List<TechMatchRequirementSkillModel> backEndSkills;
			List<TechMatchRequirementSkillModel> infraSkills;
			List<TechMatchRequirementSkillModel> mlSkills;
			List<TechMatchRequirementSkillModel> nativeApplicationSkills;
			//IDを取得する
			int id = bookmark.getApplicationKey().getId();
			// 応募したID情報より募集情報を取得する
			TechMatchRequirementEntity requirementEntity= techMatchRequirementInfoRepository.findById(id).get();
			techMatchRequirementModel = requirementEntity2requirementModel(requirementEntity);
			// 募集情報のステータスを取得する
			TechMatchRequirementStatusEntity requirementStatus = techMatchRequirementStatusRepository.findById(requirementEntity.getRequirementStatusCd()).get();
			techMatchRequirementModel.setRequirementStatus(requirementStatus.getName());
			// 応募したID情報より各スキル情報を取得する
			List<TechMatchRequirementSkillResultEntity> frontEndSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.FRONT_END);
			List<TechMatchRequirementSkillResultEntity> backEndSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.BACK_END);
			List<TechMatchRequirementSkillResultEntity> infraSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.INFRASTRUCTURE);
			List<TechMatchRequirementSkillResultEntity> mlSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.MACHINE_LEARNING);
			List<TechMatchRequirementSkillResultEntity> nativeApplicationSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(id,TechMatchSkillTypeEnum.NATIVE_APPLICATION);
			// 取得したスキル情報をエンティティからモデルに変換する
			frontEndSkills =  entitySKill2modelSkill(frontEndSkillEntities);
			backEndSkills = entitySKill2modelSkill(backEndSkillEntities);
			infraSkills = entitySKill2modelSkill(infraSkillEntities);
			mlSkills = entitySKill2modelSkill(mlSkillEntities);
			nativeApplicationSkills = entitySKill2modelSkill(nativeApplicationSkillEntities);
			// それぞれをTechMatchRequirementAndSkillsModelにいれる
			requirementAndSkillsModel.setRequirementModel(techMatchRequirementModel);
			requirementAndSkillsModel.setFrontEndSkills(frontEndSkills);
			requirementAndSkillsModel.setBackEndSkills(backEndSkills);
			requirementAndSkillsModel.setInfraSkills(infraSkills);
			requirementAndSkillsModel.setMlSkills(mlSkills);
			requirementAndSkillsModel.setNativeApplicationSkills(nativeApplicationSkills);
			// Listに追加する
			requirementAndSkillsModelsList.add(requirementAndSkillsModel);
		}
		return requirementAndSkillsModelsList;
	}



	@Override
	public void cancelBookmark(TechMatchBookmarksKey bookmarkKey) throws TechMatchException {
		if(bookmarkKey != null) {
			techMatchBookmarksRepository.deleteById(bookmarkKey);
		}
		else {
			throw new TechMatchRequirementException("ブックマーク情報を読み取れませんでした");
		}
	}
	@Override
	public void cancelApplication(TechMatchApplicationKey applicationKey) throws TechMatchException {
	    if(applicationKey != null) {
			logger.info(applicationKey.getUserId()+" "+applicationKey.getId()+" "+"応募情報をキャンセルしました");
			techMatchApplicationRepository.deleteById(applicationKey);
		}
		else {
			throw new TechMatchRequirementException("応募情報を読み取れませんでした");
		}
	}

	@Override
	public void apply(TechMatchApplicationKey applicationKey) throws TechMatchException {
	    if(applicationKey != null) {
	    	Integer recuireId = applicationKey.getId();
	    	TechMatchRequirementEntity toApplyRequirementEntity = techMatchRequirementInfoRepository.findById(recuireId).orElseThrow(()->new TechMatchRequirementException("募集情報が存在しません"));
			if(toApplyRequirementEntity.getExpireDate().before(new java.util.Date())) {
				throw new TechMatchRequirementException("募集期限が過ぎています");
			}
			logger.info(applicationKey.getUserId()+" "+applicationKey.getId()+" "+"応募しました");
	    	TechMatchApplicationEntity applicationEntity = new TechMatchApplicationEntity(applicationKey);
			techMatchApplicationRepository.save(applicationEntity);
		}
		else {
			throw new TechMatchRequirementException("応募情報を読み取れませんでした");
		}
	}

	@Override
	public void bookmark(TechMatchBookmarksKey bookmarkKey) throws TechMatchException {
		if(bookmarkKey != null) {
			TechMatchBookmarksEntity bookmarkEntity = new TechMatchBookmarksEntity(bookmarkKey);
			techMatchBookmarksRepository.save(bookmarkEntity);
		}
		else {
			throw new TechMatchRequirementException("ブックマーク情報を読み取れませんでした");
		}
	}
	
	@Override
	public boolean confirmIsMatched(String userId,Integer requirementId) throws TechMatchException {
		if(requirementId < 0 || Objects.isNull(requirementId)) {
			throw new TechMatchRequirementException("正しい募集IDを指定してください");
		}
		try {
			Boolean isMatched = techMatchApplicationRepository.confirmIsMatched(userId, requirementId);
			if(Objects.isNull(isMatched)) {
				return false;
			}
			return isMatched;
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("ユーザID："+ userId + " 募集ID："+ requirementId + "マッチ状態確認中にエラーが発生しました");
			throw new TechMatchRequirementException("マッチ状態確認中にエラーが発生しました");
		}
	}
}
