package com.techmatch.base.common.model.user;

import java.util.List;

import lombok.Data;

@Data
public class TechMatchUserAllInfoToUpdateModel {
	// TODO 更新用のモデルを用意する
	private TechMatchUserFrontModel userFrontModel;
	List<TechMatchUserSKillToUpdateModel> skills;

}
