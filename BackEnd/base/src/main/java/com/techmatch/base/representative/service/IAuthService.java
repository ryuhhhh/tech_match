package com.techmatch.base.representative.service;

import com.techmatch.base.common.model.user.TechMatchUserModel;
import com.techmatch.base.common.model.user.TechMatchUserFrontModel;
import com.techmatch.base.exception.TechMatchException;

public interface IAuthService {
	/**
           * 登録処理を行います
	 * @return 
	 * @throws Exception 
 	 */
	void techMatchUserRegistrationService(TechMatchUserModel user) throws TechMatchException, Exception;
	
}
