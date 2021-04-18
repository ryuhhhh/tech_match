package com.techmatch.base.common.model.user;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
/**
 * フロント用モデル パスワードを含めないver.
 * @author firmy
 *
 */
public class TechMatchUserFrontModel {
	@NotNull
	@Size(min=6,max=50)
	private String userId;
	@Size(min=6,max=50)
	private String mailAddress;
	@Size(min=0,max=100)
	private String twitterAccount;
	@Size(min=0,max=100)
	private String facebookAccount;
	@Size(min=0,max=400)
	private String introduce;
	@Size(max=50)
	private String userImage;
	private Date createDate;
	private Date updateDate;
}
