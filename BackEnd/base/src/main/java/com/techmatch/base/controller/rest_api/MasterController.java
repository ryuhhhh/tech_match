package com.techmatch.base.controller.rest_api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementStatusEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillCodeEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillJenreCodeEntity;
import com.techmatch.base.common.entity.skill.TechMatchSkillLevelEntity;
import com.techmatch.base.common.utility.TechMatchSkillTypeEnum;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementStatusRepository;
import com.techmatch.base.representative.repository.skill.TechMatchSkillJenreRepository;
import com.techmatch.base.representative.repository.skill.TechMatchSkillLevelRepository;
import com.techmatch.base.representative.repository.skill.TechMatchSkillRepository;

/**
 * 様々なマスターテーブルの情報を取得します
 */
@RequestMapping("/api/master")
@RestController
public class MasterController {

	@Autowired
	TechMatchRequirementStatusRepository techMatchRequirementStatusRepository;
	@Autowired
	TechMatchSkillLevelRepository techMatchSkillLevelRepository;
	@Autowired
	TechMatchSkillJenreRepository techMatchSkillJenreRepository;
	@Autowired
	TechMatchSkillRepository techMatchSkillRepository;

	/* 募集状態一覧  TechMatchRequirementStatusEntity TechMatchRequirementStatusRepository*/
	/*スキルレベル一覧 TechMatchSkillLevelEntity TechMatchSkillLevelRepository*/
	/*スキルジャンル TechMatchSkillJenreCodeEntity TechMatchSkillJenreRepository*/
	/*フロントエンドスキル TechMatchSkillCodeEntity TechMatchSkillRepository*/
	/*バックエンドスキル TechMatchSkillCodeEntity TechMatchSkillRepository*/
	/*インフラスキル TechMatchSkillCodeEntity TechMatchSkillRepository*/
	/*機械学習スキル TechMatchSkillCodeEntity TechMatchSkillRepository*/
	/*ネイティブアプリスキル TechMatchSkillCodeEntity TechMatchSkillRepository*/
	@GetMapping("/tables")
	@ResponseStatus(code = HttpStatus.OK)
	public  Map  getMasterTables() {
        Map map = new HashMap();
		List<TechMatchRequirementStatusEntity> requirementStatusEntityList = techMatchRequirementStatusRepository.findAll();
		List<TechMatchSkillLevelEntity> skillLevelEntityList = techMatchSkillLevelRepository.findAll();
		List<TechMatchSkillJenreCodeEntity> skillJenreCodeEntityList = techMatchSkillJenreRepository.findAll();
		List<TechMatchSkillCodeEntity> fronEndSkillCodeEntity = techMatchSkillRepository.getSKillMaster(TechMatchSkillTypeEnum.FRONT_END.getJenreCd());
		List<TechMatchSkillCodeEntity> backEndSkillCodeEntity = techMatchSkillRepository.getSKillMaster(TechMatchSkillTypeEnum.BACK_END.getJenreCd());
		List<TechMatchSkillCodeEntity> infraSkillCodeEntity = techMatchSkillRepository.getSKillMaster(TechMatchSkillTypeEnum.INFRASTRUCTURE.getJenreCd());
		List<TechMatchSkillCodeEntity> mlSkillCodeEntity = techMatchSkillRepository.getSKillMaster(TechMatchSkillTypeEnum.MACHINE_LEARNING.getJenreCd());
		List<TechMatchSkillCodeEntity> nativeApplicationSkillCodeEntity = techMatchSkillRepository.getSKillMaster(TechMatchSkillTypeEnum.NATIVE_APPLICATION.getJenreCd());
		map.put("requirementStatusEntityList", requirementStatusEntityList);
		map.put("skillLevelEntityList", skillLevelEntityList);
		map.put("skillJenreCodeEntityList",skillJenreCodeEntityList);
		map.put("fronEndSkillCodeEntity",fronEndSkillCodeEntity);
		map.put("backEndSkillCodeEntity",backEndSkillCodeEntity);
		map.put("infraSkillCodeEntity",infraSkillCodeEntity);
		map.put("mlSkillCodeEntity",mlSkillCodeEntity);
		map.put("nativeApplicationSkillCodeEntity",nativeApplicationSkillCodeEntity);
		Map result = new HashMap();
        result.put("result",map);
		return result;
	}
}
