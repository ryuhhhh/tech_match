package com.techmatch.base.logic.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.techmatch.base.common.entity.user.TechMatchTmpUserEntity;
import com.techmatch.base.common.error.BadRequestExceptionHandler;
import com.techmatch.base.common.model.user.TechMatchUserModel;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.exception.TechMatchValidationException;
import com.techmatch.base.logic.repository.TechMatchAuthRepositoryExecution;
import com.techmatch.base.representative.service.IAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService{
	@Autowired
	BadRequestExceptionHandler badRequestExceptionHandler;
	@Autowired
	TechMatchAuthRepositoryExecution techMatchAuthRepositoryExecution;

	/**
	 * ユーザ情報を仮登録します
	 */
	@Override
	public void techMatchUserRegistrationService(TechMatchUserModel user) throws TechMatchException{
        // Validator を取得
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
		// ユーザの情報をバリデーション
        Set<ConstraintViolation<TechMatchUserModel>> result = validator.validate(user);
        if(result.size()>0) {
        	Map<String,String> errorMessages=new HashMap<>();
        	for(ConstraintViolation<TechMatchUserModel> violation:result) {
        		errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
        	}
        	throw new TechMatchValidationException(errorMessages);
        }
        // リポジトリを呼ぶ
        try {
        	techMatchAuthRepositoryExecution.registerUser(user);
		} catch (TechMatchException e) {
			throw e;
		}
	}
	
	
	/**
	 * ユーザ情報を登録します
	 */
//	@Override
//	public TechMatchUserFrontModel techMatchUserRegistrationService(TechMatchUserModel user) throws TechMatchException{
//        // Validator を取得
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//		// ユーザの情報をバリデーション
//        Set<ConstraintViolation<TechMatchUserModel>> result = validator.validate(user);
//        if(result.size()>0) {
//        	Map<String,String> errorMessages=new HashMap<>();
//        	for(ConstraintViolation<TechMatchUserModel> violation:result) {
//        		errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
//        	}
//        	throw new TechMatchValidationException(errorMessages);
//        }
//        // リポジトリを呼ぶ
//        try {
//			TechMatchUserFrontModel userResponseModel = techMatchAuthRepositoryExecution.registerUser(user);
//			return userResponseModel;
//		} catch (TechMatchException e) {
//			throw e;
//		}
//	}
	
}
