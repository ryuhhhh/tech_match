package com.techmatch.base.common.model.user;

import lombok.Data;

import java.util.Date;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class TechMatchUserModel {
	@NotNull
	@Size(min=1,max=50)
	// TODO 英文字記号指定にする
	private String userId;
	@Size(min=6,max=50)
	@Pattern(regexp = "[a-zA-Z0-9!#$%'()+-]+", message = "半角英数字 !#$%\\'()+-を使用してください")
	// TODO 英文字記号指定にする
	private String password;
//	@Pattern(regexp="")
	@Size(min=6,max=50)
	private String mailAddress;
	@Size(min=0,max=100)
	private String twitterAccount;
	@Size(min=0,max=100)
	private String facebookAccount;
	// 登録後
	@Size(min=0,max=400)
	private String introduce;
	// ユーザのファイルの名前を入れる
	// 登録後
	@Size(max=50)
	private String userImage;

	private Date createDate;
	private Date updateDate;
}
