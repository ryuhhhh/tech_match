package com.techmatch.base.common.model.application;

import java.util.List;

import com.techmatch.base.common.model.requirement.TechMatchRequirementModel;

import lombok.Data;

@Data
public class TechMatchBookmarksModel {
	// ブックマークした募集のIDのリストの配列
	private List<TechMatchRequirementModel> bookmarks;
}
