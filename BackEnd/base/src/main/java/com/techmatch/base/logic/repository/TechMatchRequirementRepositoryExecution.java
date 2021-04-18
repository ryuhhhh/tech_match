package com.techmatch.base.logic.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techmatch.base.common.entity.application.TechMatchApplicationEntity;
import com.techmatch.base.common.entity.application.TechMatchApplicationKey;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementSkillResultEntity;
import com.techmatch.base.common.entity.requirement.TechMatchRequirementStatusEntity;
import com.techmatch.base.common.entity.user.TechMatchUserDescribeSkillEntity;
import com.techmatch.base.common.model.requirement.TechMatchListSearchModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementModel;
import com.techmatch.base.common.model.requirement.TechMatchRequirementSkillModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementAndSkillsToApplyModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementSkillToApplyModel;
import com.techmatch.base.common.model.requirement.apply.TechMatchRequirementToApplyModel;
import com.techmatch.base.common.model.user.TechMatchUserAllSkillModel;
import com.techmatch.base.common.model.user.TechMatchUserSkillModel;
import com.techmatch.base.common.utility.TechMatchRequirementStatusEnum;
import com.techmatch.base.common.utility.TechMatchSkillTypeEnum;
import com.techmatch.base.controller.rest_api.AuthController;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.exception.TechMatchRequirementException;
import com.techmatch.base.logic.service.TechMatchRequirementSpecification;
import com.techmatch.base.representative.repository.applicant.TechMatchApplicationRepository;
import com.techmatch.base.representative.repository.applicant.TechMatchBookmarksRepository;
import com.techmatch.base.representative.repository.natural.ITechMatchRequirementRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementInfoRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementSkillResultRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementSkillToApplyRepository;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementStatusRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserDescribeSkillRepository;

import static com.techmatch.base.common.utility.TechMatchConverter.entitySKill2modelSkill;
import static com.techmatch.base.common.utility.TechMatchConverter.requirementEntity2requirementModel;
import static com.techmatch.base.common.utility.TechMatchConverter.toApplyRequirementModel2toApplyRequirementEntity;
import static com.techmatch.base.common.utility.TechMatchImageUtil.createFile;
import static com.techmatch.base.common.utility.TechMatchImageUtil.fileCheck;
import static com.techmatch.base.common.utility.TechMatchConverter.entitySKill2modelUserSkill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TechMatchRequirementRepositoryExecution implements ITechMatchRequirementRepository{
	@Autowired
	TechMatchRequirementInfoRepository techMatchRequirementInfoRepository;
	@Autowired
	TechMatchRequirementSkillToApplyRepository techMatchRequirementSkillToApplyRepository;
	@Autowired
	TechMatchRequirementStatusRepository techMatchRequirementStatusRepository;
	@Autowired
	TechMatchRequirementSkillResultRepository techMatchSkillRepository;
    protected final static Logger logger = LoggerFactory.getLogger(TechMatchRequirementRepositoryExecution.class);

	/**
	 * 募集する
	 * @param images 
	 * @throws IOException 
	 */
	@Override
	public TechMatchRequirementAndSkillsModel recruit(TechMatchRequirementToApplyModel requirementAndSkillsToApplyModel,List<List<TechMatchRequirementSkillToApplyModel>> skillLists)throws TechMatchException{
		// モデルからエンティティに変換する
		TechMatchRequirementEntity techMatchRequirementEntity = toApplyRequirementModel2toApplyRequirementEntity(requirementAndSkillsToApplyModel);
		if(techMatchRequirementEntity.getImage_1()!=null) {
			techMatchRequirementEntity.setImage_1(techMatchRequirementEntity.getImage_1()+new Date());
		}
		if(techMatchRequirementEntity.getImage_2()!=null) {
			techMatchRequirementEntity.setImage_2(techMatchRequirementEntity.getImage_2()+new Date());
		}
		// 募集ステータスを入力する
		techMatchRequirementEntity.setRequirementStatusCd(TechMatchRequirementStatusEnum.NOW.getStatusCd());
		Integer savedRequirementId=null;
		// エンティティを使用して募集情報を登録し、募集IDを取得する
		try {
			TechMatchRequirementEntity savedEntity = techMatchRequirementInfoRepository.save(techMatchRequirementEntity);
			savedRequirementId = savedEntity.getId();
			if(Objects.isNull(savedRequirementId)){
				throw new TechMatchRequirementException("募集情報の登録に失敗しました");
			}
		}catch(TechMatchRequirementException e) {
			throw e;
		}
		
		// 募集IDを使用し、スキルを登録する
		List<List<TechMatchRequirementSkillEntity>> listOfSavedSkillEntityList = new ArrayList<>();
		for(List<TechMatchRequirementSkillToApplyModel> skillList :skillLists) {
			if(skillList.size()>0) {
				List<TechMatchRequirementSkillEntity> savedSkillEntityList = new ArrayList<>();
				for(TechMatchRequirementSkillToApplyModel skillModel : skillList) {
					if(skillModel!=null) {
						// try-catch
						TechMatchRequirementSkillEntity skillEntity = new TechMatchRequirementSkillEntity();
						skillEntity.setId(savedRequirementId);
						skillEntity.setSkillCd(skillModel.getSkillCd());
						skillEntity.setSkillLevelCd(skillModel.getSkillLevelCd());
						// 情報を登録する
						savedSkillEntityList.add(techMatchRequirementSkillToApplyRepository.save(skillEntity));
					}
				}
				listOfSavedSkillEntityList.add(savedSkillEntityList);
			}
		}
		logger.info("id:"+savedRequirementId+" 募集を開始しました");
		// 結果を取得する
		return getSavedRequirementAndSkillsModel(savedRequirementId);
	}
	
	/**
	 * 募集画像を登録
	 */
	public void registerRequireImages(List<MultipartFile> images,int requirementId) throws TechMatchException{
		/*画像の登録*/
		List<String> fileNames = new ArrayList<>();
		StringBuffer filePath = new StringBuffer("images/requirements");
		for(MultipartFile image : images) {
			String extention;
			// ファイルのチェック
			try {
				extention = fileCheck(image);
			}catch(TechMatchException e) {
				// 募集情報を削除する
				logger.error("画像情報登録失敗により募集情報を削除しました");
				throw new TechMatchRequirementException("画像情報登録失敗により募集情報を削除しました");
			}
			// ファイルの作成
			StringBuffer fileName = new StringBuffer(requirementId+"requirement").append(System.currentTimeMillis()).append("."+extention);
			fileNames.add(fileName.toString());
			try {
				createFile(image, filePath.toString(), fileName.toString());
			}catch(IOException e) {
				// 募集情報を削除する
				throw new TechMatchRequirementException("画像情報登録失敗により募集情報を削除しました");
			}
		}
		// 画像情報の登録
		if(fileNames.size()==2) {
			techMatchRequirementInfoRepository.updateRequirementImagesInfo(requirementId,fileNames.get(0),fileNames.get(1));
			logger.info("id:"+requirementId+" 画像を登録しました "+fileNames.get(0)+" "+fileNames.get(1));
		}
		else if(fileNames.size()==1) {
			techMatchRequirementInfoRepository.updateRequirementImageInfo(requirementId,fileNames.get(0));	
			logger.info("id:"+requirementId+" 画像を登録しました "+fileNames.get(0));
		}
	}
	
	/**
	 * 募集IDを使用し、DBから募集結果を探す
	 */
	public TechMatchRequirementAndSkillsModel getSavedRequirementAndSkillsModel(Integer savedRequirementId) {
		TechMatchRequirementAndSkillsModel requirementAndSkillsModel = new TechMatchRequirementAndSkillsModel();
		TechMatchRequirementModel techMatchRequirementModel = new TechMatchRequirementModel();
		List<TechMatchRequirementSkillModel> frontEndSkills;
		List<TechMatchRequirementSkillModel> backEndSkills;
		List<TechMatchRequirementSkillModel> infraSkills;
		List<TechMatchRequirementSkillModel> mlSkills;
		List<TechMatchRequirementSkillModel> nativeApplicationSkills;
		// 応募したID情報より募集情報を取得する
		TechMatchRequirementEntity requirementEntity= techMatchRequirementInfoRepository.findById(savedRequirementId).get();
		techMatchRequirementModel = requirementEntity2requirementModel(requirementEntity);
		// 募集情報のステータスを取得する
		TechMatchRequirementStatusEntity requirementStatus = techMatchRequirementStatusRepository.findById(requirementEntity.getRequirementStatusCd()).get();
		techMatchRequirementModel.setRequirementStatus(requirementStatus.getName());
		// 応募したID情報より各スキル情報を取得する
		List<TechMatchRequirementSkillResultEntity> frontEndSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(savedRequirementId,TechMatchSkillTypeEnum.FRONT_END);
		List<TechMatchRequirementSkillResultEntity> backEndSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(savedRequirementId,TechMatchSkillTypeEnum.BACK_END);
		List<TechMatchRequirementSkillResultEntity> infraSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(savedRequirementId,TechMatchSkillTypeEnum.INFRASTRUCTURE);
		List<TechMatchRequirementSkillResultEntity> mlSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(savedRequirementId,TechMatchSkillTypeEnum.MACHINE_LEARNING);
		List<TechMatchRequirementSkillResultEntity> nativeApplicationSkillEntities= techMatchSkillRepository.getRequirementfrontEndSkillsFromRepository(savedRequirementId,TechMatchSkillTypeEnum.NATIVE_APPLICATION);
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

		return requirementAndSkillsModel;
	}


	/**
	 * 募集をキャンセルする
	 */
	@Override
	public void cancelRequirement(Integer id, String userId) throws TechMatchException {
		// 募集状態コードを変更する
		try {
			logger.info("id:"+id+"userId "+userId+" 募集をキャンセルしました");
			techMatchRequirementInfoRepository.changeRequirementStatus(id, userId, TechMatchRequirementStatusEnum.CANCELD.getStatusCd());
		}catch(Exception e) {
			throw new TechMatchRequirementException("募集の取り止めに失敗しました。管理者にご一報ください。");
		}
	}

	/**
	 * 募集を終了する
	 */
	@Override
	public void closeRequirement(Integer id, String userId) throws TechMatchException {
		// 募集状態コードを変更する
		try {
			logger.info("id:"+id+"userId "+userId+" 募集を終了しました");
			techMatchRequirementInfoRepository.changeRequirementStatus(id, userId, TechMatchRequirementStatusEnum.CLOSE.getStatusCd());
		}catch(Exception e) {
			System.out.println(e);
			throw new TechMatchRequirementException("募集の終了に失敗しました。管理者にご一報ください。");
		}
	}

	@Override
	public void resumeRequirement(Integer id, String userId) throws TechMatchException {
		// 募集状態コードを変更する
		try {
			logger.info("id:"+id+"userId "+userId+" 募集を再開しました");
			techMatchRequirementInfoRepository.changeRequirementStatus(id, userId, TechMatchRequirementStatusEnum.NOW.getStatusCd());
		}catch(Exception e) {
			throw new TechMatchRequirementException("募集の再開に失敗しました。管理者にご一報ください。");
		}
	}

	@Autowired
	TechMatchApplicationRepository techMatchApplicationRepository;
	@Autowired
	TechMatchUserDescribeSkillRepository techMatchApplicantSkillRepository;
	/**
	 * 応募者の情報を取得します
	 */
	@Override
	public List<TechMatchUserAllSkillModel> getApplicantUsersSkill(Integer id, String requirerId) throws TechMatchException {
		// ユーザのスキル情報の保存場所を指定する
		List<TechMatchUserAllSkillModel> userAllSkillModelList = new ArrayList<>();
		// 応募者一覧を取得する
		List<TechMatchApplicationEntity> applicantList = techMatchApplicationRepository.getApplicantSkill(id);
		// ユーザのスキルの保存場所を用意する
		List<TechMatchUserSkillModel> frontEndSkills;
		List<TechMatchUserSkillModel> backEndSkills;
		List<TechMatchUserSkillModel> infraSkills;
		List<TechMatchUserSkillModel> mlSkills;
		List<TechMatchUserSkillModel> nativeApplicationSkills;

		for(TechMatchApplicationEntity applicant:applicantList) {
			TechMatchUserAllSkillModel userAllSkillModel = new TechMatchUserAllSkillModel();
			frontEndSkills = entitySKill2modelUserSkill(techMatchApplicantSkillRepository.getUserSKills(applicant.getApplicationKey().getUserId(), TechMatchSkillTypeEnum.FRONT_END.getJenreCd()));
			backEndSkills = entitySKill2modelUserSkill(techMatchApplicantSkillRepository.getUserSKills(applicant.getApplicationKey().getUserId(), TechMatchSkillTypeEnum.BACK_END.getJenreCd()));
			infraSkills = entitySKill2modelUserSkill(techMatchApplicantSkillRepository.getUserSKills(applicant.getApplicationKey().getUserId(), TechMatchSkillTypeEnum.INFRASTRUCTURE.getJenreCd()));
			mlSkills = entitySKill2modelUserSkill(techMatchApplicantSkillRepository.getUserSKills(applicant.getApplicationKey().getUserId(), TechMatchSkillTypeEnum.MACHINE_LEARNING.getJenreCd()));
			nativeApplicationSkills = entitySKill2modelUserSkill(techMatchApplicantSkillRepository.getUserSKills(applicant.getApplicationKey().getUserId(), TechMatchSkillTypeEnum.NATIVE_APPLICATION.getJenreCd()));
			userAllSkillModel.setUserId(applicant.getApplicationKey().getUserId());
			userAllSkillModel.setFrontEndSkills(frontEndSkills);
			userAllSkillModel.setBackEndSkills(backEndSkills);
			userAllSkillModel.setInfraSkills(infraSkills);
			userAllSkillModel.setNativeApplicationSkills(nativeApplicationSkills);
			userAllSkillModel.setMlSkills(mlSkills);
			userAllSkillModel.setAdoption(applicant.isAdoption());
			userAllSkillModelList.add(userAllSkillModel);
		}
		return userAllSkillModelList;
	}

	@Autowired
	TechMatchRequirementSpecification techMatchRequirementSpecification;
	/**
	 * 応募情報のリストを返します
	 */
	@Override
	public Page<TechMatchRequirementEntity> getRequirementList(TechMatchListSearchModel listSearchModel, Pageable pageable) throws TechMatchException {
		/* 全文検索を実施する listSearchModel.getFullTextSearchWords() */
		return techMatchRequirementSpecification.findAll(listSearchModel.getFullTextSearchWords(),listSearchModel.getSkillMap(),pageable);
	}

	/**
	 * 応募者を決定します
	 */
	@Override
	public void decideApplicant(TechMatchApplicationKey key, String requirerUserId) throws TechMatchException {
		logger.info("requirerUserId:"+requirerUserId+"requirer "+requirerUserId+" userId "+key.getUserId()+" 応募者を決定しました1");
		techMatchApplicationRepository.decideApplicant(key,key.getId(),requirerUserId);
	}
	@Override
	public void cancelApplication(TechMatchApplicationKey key, String requirerUserId) throws TechMatchException {

	}
}
