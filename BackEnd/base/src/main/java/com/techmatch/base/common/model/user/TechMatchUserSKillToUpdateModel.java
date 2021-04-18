package com.techmatch.base.common.model.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TechMatchUserSKillToUpdateModel {
	@NotNull
	@Size(max=30)
	private String skillCd;
	@NotNull
	@Size(max=30)
	private String skillLevelCd;
}
