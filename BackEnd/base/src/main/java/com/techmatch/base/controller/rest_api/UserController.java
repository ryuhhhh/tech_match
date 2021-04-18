package com.techmatch.base.controller.rest_api;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techmatch.base.common.model.requirement.TechMatchRequirementAndSkillsModel;
import com.techmatch.base.common.model.user.TechMatchUserAllInfoModel;
import com.techmatch.base.common.model.user.TechMatchUserAllInfoToUpdateModel;
import com.techmatch.base.exception.TechMatchAuthException;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.exception.TechMatchValidationException;
import com.techmatch.base.logic.repository.TechMatchRequirementRepositoryExecution;
import com.techmatch.base.logic.service.TechMatchUserServiceImpl;
import com.techmatch.base.representative.repository.user.TechMatchUserRepository;

import static com.techmatch.base.common.utility.TechMatchImageUtil.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	TechMatchUserRepository techMatchUserRepository;
	@Autowired
	MessageSource messageSource;
    protected final static Logger logger = LoggerFactory.getLogger(UserController.class);
	/**
	 * ユーザIDが存在しているか返す
	 * @param userId
	 * @return
	 * @throws TechMatchException
	 */
	@GetMapping("/isUserIdExist/{userId}")
	@ResponseStatus(code = HttpStatus.OK)
	public boolean isUserIdExist(@PathVariable("userId") String userId) throws TechMatchException {
		if(userId == null || userId.length() <1) {
			throw new TechMatchValidationException(messageSource.getMessage("BASE.AUTH.ALREADY_USER_ID_EXISTED",null,null));
		}
		if(techMatchUserRepository.isUserIdAlreadyExisted(userId).size()>0) {
			return true;
			//			errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_FACEBOOK_ACCOUNT_EXISTED", null, null));
		}
		return false;
	}

	/**
	 * メールアドレスが存在しているか返す
	 * @param userId
	 * @return
	 * @throws TechMatchException
	 */
	@GetMapping("/isMailAddressExist/{mailAddress}")
	@ResponseStatus(code = HttpStatus.OK)
	public boolean isMailAddressExist(@PathVariable("mailAddress") String mailAddress) throws TechMatchException {
		if(mailAddress == null || mailAddress.length() <1) {
			throw new TechMatchValidationException(messageSource.getMessage("BASE.AUTH.ALREADY_USER_ID_EXISTED",null,null));
		}
		if(techMatchUserRepository.isMailAddressAlreadyExisted(mailAddress).size()>0) {
			return true;
			//			errorMessages.add(messageSource.getMessage("BASE.AUTH.ALREADY_FACEBOOK_ACCOUNT_EXISTED", null, null));
		}
		return false;
	}
	
	@Autowired
	TechMatchUserServiceImpl techMatchUserServiceImpl;

	/**
	 * 特定ユーザの全情報を返す
	 * @param userId
	 * @return
	 * @throws TechMatchException
	 */
	@GetMapping("/getInfo/{userId}")
	@ResponseStatus(code = HttpStatus.OK)
	public TechMatchUserAllInfoModel getUserAllInfo(@PathVariable("userId") String userId) throws TechMatchException{
		try {
			return techMatchUserServiceImpl.getUserAllInfo(userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}

	/**
	 * 自身の募集情報を取得する
	 */
	@GetMapping("/requirement-list")
	@ResponseStatus(code = HttpStatus.OK)
	public List<TechMatchRequirementAndSkillsModel> getRequirementList()  throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		try {
			// サービスを呼び全募集情報を取得する
			return techMatchUserServiceImpl.getRequirementInfo(userId);
		}catch(TechMatchException e) {
			throw e;
		}
	}
	
	/**
	 * 自身のユーザ情報を更新する
	 */
	@PostMapping("/myupdate")
	@ResponseStatus(code = HttpStatus.OK)
	public TechMatchUserAllInfoModel updateUserInfo(@RequestBody TechMatchUserAllInfoToUpdateModel allInfoToUpdateModel) throws TechMatchException{
		// ユーザIDを一度空にする
		allInfoToUpdateModel.getUserFrontModel().setUserId(null);
		// ユーザIDを認証情報より取得する
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		if(Objects.isNull(userId) || userId.length() <=0) {
			throw new TechMatchAuthException("認証情報を取得できませんでした");
		}
		// 認証情報より取得したユーザIDをセットする
		allInfoToUpdateModel.getUserFrontModel().setUserId(userId);
		// サービスを呼ぶ
		TechMatchUserAllInfoModel allInfoModel ;
		try {
			// 情報を更新する
			techMatchUserServiceImpl.updateUserInfo(allInfoToUpdateModel);
			// 更新後の情報を取得する
			allInfoModel = techMatchUserServiceImpl.getUserAllInfo(userId);
		}catch(TechMatchException e) {
			throw e;
		}
		return allInfoModel;
	}
	
	/**
	 * 自身のイメージ画像を保存する
	 * PUTではなくPOSTで。。。
	 * @throws Exception 
	 */
	@PostMapping("/my-image-update")
	@ResponseStatus(code = HttpStatus.OK)
	public String updateUserImage(@RequestParam("userImage") MultipartFile multipartFile) throws Exception{
		// ユーザIDを認証情報より取得する
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		/*ファイルチェック 関数 arg multipartFile*/
		String extention;
		try {
			extention = fileCheck(multipartFile);
		}catch(TechMatchException e) {
			throw e;
		}
		
		/* TODO exifの削除 */

		/*削除と作成*/
		StringBuffer filePath = new StringBuffer("images/users");
		/*ファイルの削除*/
		String existedUserImageName = techMatchUserRepository.getUserImage(userId);
		if(existedUserImageName!=null && !existedUserImageName.isEmpty()) {
			deleteFile(filePath.toString()+"/"+existedUserImageName);
		}


		/*ファイルの作成*/
		userId = userId.replaceAll("[./*]", "");
		if(userId.length()==0) {
			userId = "techmatch";
		}
		StringBuffer fileName = new StringBuffer(userId).append(System.currentTimeMillis()).append("."+extention);
		try {
			createFile(multipartFile, filePath.toString(), fileName.toString());
		}catch(IOException e) {
			throw e;
		}
		
		// 画像情報の保存
		techMatchUserRepository.updateUserImage(fileName.toString(),userId);
		
		return null;
	}
	
	
	/**
	 * 自身のユーザ情報を全て削除する
	 * @throws TechMatchException
	 */
	@PostMapping("/mydelete")
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteUserInfo() throws TechMatchException{
		Authentication  auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = auth.getName();
		if(Objects.isNull(userId) || userId.length() <=0) {
			throw new TechMatchAuthException("認証情報を取得できませんでした");
		}
		try {
			techMatchUserServiceImpl.deleteUserInfo(userId);	
		} catch(TechMatchException e) {
			throw e;
		}
	}

}
