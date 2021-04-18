package com.techmatch.base.logic.repository;

import com.techmatch.base.common.entity.user.TechMatchTmpUserEntity;
import com.techmatch.base.common.entity.user.TechMatchUserEntity;
import com.techmatch.base.common.model.user.TechMatchUserModel;
import com.techmatch.base.common.model.user.TechMatchUserFrontModel;
import com.techmatch.base.exception.TechMatchAuthException;
import com.techmatch.base.exception.TechMatchDataException;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.logic.service.AuthServiceImpl;
import com.techmatch.base.representative.repository.user.TechMatchTmpUserRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserRepository;

import static com.techmatch.base.common.utility.TechMatchConverter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomStringUtils;


@Service
public class TechMatchAuthRepositoryExecution {
	@Autowired
	TechMatchUserRepository techMatchAuthRepository;
	@Autowired
	TechMatchTmpUserRepository techMatchTmpUserRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MessageSource messageSource;
    @Autowired
    MailSender sender;
    protected final static Logger logger = LoggerFactory.getLogger(TechMatchAuthRepositoryExecution.class);

    /**
     * ユーザ登録を行います
     * @param user
     * @return
     * @throws TechMatchException
     */
	public void registerUser(TechMatchUserModel user) throws TechMatchException {
		// モデルとエンティティの交換
		TechMatchTmpUserEntity tmpUserEntity= modelUser2TmpEntityUser(user);
		TechMatchTmpUserEntity savedTmpUserEntity;
		// すでにアカウント情報が存在しているか調査
		List<String> errorMessages = new ArrayList<>();
		try {
			if(techMatchAuthRepository.isUserIdAlreadyExisted(user.getUserId()).size()>0 || techMatchTmpUserRepository.isUserIdAlreadyExisted(user.getUserId()).size()>0) {
				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_USER_ID_EXISTED", null, null));
			}
			if(techMatchAuthRepository.isMailAddressAlreadyExisted(user.getMailAddress()).size()>0 || techMatchTmpUserRepository.isMailAddressAlreadyExisted(user.getMailAddress()).size()>0) {
				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_MAIL_ADDRESS_EXISTED", null, null));
			}
			if(errorMessages.size()>0) {
				throw new TechMatchAuthException(errorMessages);
			}
		}catch(TechMatchAuthException e) {
			throw e;
		}

		String tmpUserId = RandomStringUtils.randomAlphanumeric(16);
		// ハッシュ化
		tmpUserEntity.setPassword(passwordEncoder.encode(tmpUserEntity.getPassword()));
		// 一時ユーザIDを保存
		tmpUserEntity.setTmpUserId(tmpUserId);
		
		// インサート
		try {
			savedTmpUserEntity = techMatchTmpUserRepository.saveAndFlush(tmpUserEntity);
		    if(savedTmpUserEntity==null) {
		    	throw new TechMatchDataException(messageSource.getMessage("BASE.REPOSITORY.DATABASE.FAILED_TO_REGISTRATION ", null, null));
		    }
		    logger.info("ユーザを仮登録しました"+savedTmpUserEntity.getUserId());
		} catch(TechMatchDataException e) {
			throw e;
		}
		catch(Exception e) {
			throw new TechMatchAuthException(new ArrayList<String>(Arrays.asList("登録に失敗しました")));
		}
		
		// メール送信
        String content = savedTmpUserEntity.getUserId() + "さん" + "\n" + "\n" + "以下のリンクにアクセスし登録を完了してください。" + "\n"
                +"https://www.techmatch.tokyo"
                + "/main-registration"+ "?id=" + savedTmpUserEntity.getTmpUserId();
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("support@techmatch.tokyo");
            msg.setTo(savedTmpUserEntity.getMailAddress());
            msg.setSubject("TechMatch 登録のお願い");
            msg.setText(content); 
            this.sender.send(msg);
		    logger.info("メールを送信しました"+savedTmpUserEntity.getMailAddress());
        } catch (Exception e) {
        	e.printStackTrace();
        	techMatchTmpUserRepository.delete(savedTmpUserEntity);
		    logger.error("ユーザ仮登録を取り消しました"+savedTmpUserEntity.getUserId());
        	throw new TechMatchAuthException(new ArrayList<String>(Arrays.asList("メール送信に失敗")));
        }
	}
    
    /**
     * ユーザ登録を行います
     * @param user
     * @return
     * @throws TechMatchException
     */
//	public TechMatchUserFrontModel registerUser(TechMatchUserModel user) throws TechMatchException {
//		// モデルとエンティティの交換
//		TechMatchUserEntity entityUser = modelUser2EntityUser(user);
//		TechMatchUserEntity savedEntityUser;
//		TechMatchUserEntity existedEntityUser;
//		TechMatchUserFrontModel userResponseModel;
//		// すでにアカウント情報が存在しているか調査
//		List<String> errorMessages = new ArrayList<>();
//		try {
//			if(techMatchAuthRepository.isUserIdAlreadyExisted(user.getUserId()).size()>0) {
//				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_USER_ID_EXISTED", null, null));
//			}
//			if(techMatchAuthRepository.isFaceBookAccountAlreadyExisted(user.getFacebookAccount()).size()>0) {
//				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_FACEBOOK_ACCOUNT_EXISTED", null, null));
//			}
//			if(techMatchAuthRepository.isTwitterAccountAlreadyExisted(user.getTwitterAccount()).size()>0) {
//				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_TWITTER_ACCOUNT_EXISTED", null, null));
//			}
//			if(techMatchAuthRepository.isMailAddressAlreadyExisted(user.getMailAddress()).size()>0) {
//				errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_MAIL_ADDRESS_EXISTED", null, null));
//			}
//			if(errorMessages.size()>0) {
//				throw new TechMatchAuthException(errorMessages);
//			}
//		}catch(TechMatchAuthException e) {
//			throw e;
//		}
//		// ハッシュ化
//		entityUser.setPassword(passwordEncoder.encode(entityUser.getPassword()));
//
//		// インサート
//		try {
//		    savedEntityUser = techMatchAuthRepository.save(entityUser);
//		    if(savedEntityUser==null) {
//		    	throw new TechMatchDataException(messageSource.getMessage("BASE.REPOSITORY.DATABASE.FAILED_TO_REGISTRATION ", null, null));
//		    }
//		    logger.info("ユーザを登録しました"+savedEntityUser.getUserId());
//		    userResponseModel = entityUser2ResonseModelUser(savedEntityUser);
//		    return userResponseModel;
//		} catch(TechMatchDataException e) {
//			throw e;
//		}
//	}
}
