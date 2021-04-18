package com.techmatch.base.common.model.user;

import com.techmatch.base.common.entity.user.TechMatchUserEntity;

import lombok.Data;

/**
 * ユーザの全ての情報を持つモデルです
 * 基本情報及びスキル情報を持ちます
 * @author firmy
 *
 */
@Data
public class TechMatchUserAllInfoModel {
	private TechMatchUserFrontModel techMatchUserResponseModel;
	private TechMatchUserAllSkillModel techMatchUserAllSkillModel;
}
