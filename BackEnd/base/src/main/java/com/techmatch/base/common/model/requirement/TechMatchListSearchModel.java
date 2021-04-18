package com.techmatch.base.common.model.requirement;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TechMatchListSearchModel {
	private List<String> fullTextSearchWords;
	Map<String,String> skillMap;
}
