package com.techmatch.base.controller.rest_api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techmatch.base.common.entity.user.TechMatchTmpUserEntity;
import com.techmatch.base.common.entity.user.TechMatchUserEntity;
import com.techmatch.base.common.error.BadRequestExceptionHandler;
import com.techmatch.base.common.model.auth.TechMatchAuthenticationRequestModel;
import com.techmatch.base.common.model.auth.TechMatchAuthenticationResponseModel;
import com.techmatch.base.common.model.user.TechMatchUserModel;
import com.techmatch.base.config.jwt.JwtUtil;
import com.techmatch.base.config.user.TechMatchUserDetailsService;
import com.techmatch.base.exception.TechMatchAuthException;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.exception.TechMatchValidationException;
import com.techmatch.base.logic.service.AuthServiceImpl;
import com.techmatch.base.representative.repository.user.TechMatchTmpUserRepository;
import com.techmatch.base.representative.repository.user.TechMatchUserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.techmatch.base.common.utility.TechMatchConverter.TmpEntityUser2EntityUser;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	// DIとバリデーションの実行
	@Autowired 
	AuthServiceImpl authServiceImple;
	@Autowired
	BadRequestExceptionHandler badRequestExceptionHandler;

    protected final static Logger logger = LoggerFactory.getLogger(AuthController.class);
	/**
	 * 仮登録処理を行う
	 * @param user
	 * @return
	 * @throws TechMatchException
	 */
	@PostMapping("/pre-registration")
	@ResponseStatus(HttpStatus.OK)
	public void preRegistration(@RequestBody TechMatchUserModel user) throws TechMatchException {
		try {
			authServiceImple.techMatchUserRegistrationService(user);
		} catch(TechMatchValidationException e) {
			throw new TechMatchValidationException(e.getErrorMessages());
		}catch(TechMatchAuthException e) {
			throw new TechMatchAuthException(e.getErrorMessages());
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Autowired
	TechMatchTmpUserRepository techMatchTmpUserRepository;
	@Autowired
	TechMatchUserRepository techMatchUserRepository;
	@Autowired
	PasswordEncoder encoder;
	/**
	 * 本登録処理を行う
	 * @param user
	 * @return
	 * @throws TechMatchException
	 */
	@PostMapping("/registration")
	@ResponseStatus(HttpStatus.OK)
	public  HashMap<String, String> registration(@RequestParam String id) throws TechMatchException {
		try {
			// Tempユーザに登録されているか確認
			TechMatchTmpUserEntity tmpUserEntity 
			    = techMatchTmpUserRepository.findById(id).orElseThrow(()->new TechMatchAuthException(new ArrayList<String>(Arrays.asList("仮登録されていません。"))));
			// エンティティ交換から本登録を実施
			techMatchUserRepository.save(TmpEntityUser2EntityUser(tmpUserEntity));
			// 仮登録ユーザを削除
			techMatchTmpUserRepository.deleteById(id);
			System.out.println(id);
			// ログインを実施
			return new HashMap<String,String>(){
				{put("userId",tmpUserEntity.getUserId());}
			};
//			return login(new TechMatchAuthenticationRequestModel(tmpUserEntity.getUserId(),tmpUserEntity.getPassword()));
		} 
		catch(TechMatchAuthException e) {
			logger.error("未仮登録ユーザを登録しようとしました "+id);
			throw new TechMatchAuthException(e.getErrorMessages());
		}
		catch(Exception e) {
			logger.error("本登録実施時にエラーが起こりました "+id);
			throw new TechMatchAuthException("何らかのエラーが起こりました");
		}
	}
	/**
	 * 登録処理を行う
	 * @param user
	 * @return
	 * @throws TechMatchException
	 */
//	@PostMapping("/registration")
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<?> registration(@RequestBody TechMatchUserModel user) throws TechMatchException {
//		try {
//			TechMatchUserFrontModel userResponseModel = authServiceImple.techMatchUserRegistrationService(user);
//			return login(new TechMatchAuthenticationRequestModel(userResponseModel.getUserId(),user.getPassword()));
//		} catch(TechMatchValidationException e) {
//			throw new TechMatchValidationException(e.getErrorMessages());
//		}catch(TechMatchAuthException e) {
//			throw new TechMatchAuthException(e.getErrorMessages());
//		}
//		catch(Exception e) {
//			throw e;
//		}
//	}
	
	@Autowired
	TechMatchUserDetailsService techMatchUserDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
    @Autowired
     AuthenticationManager authManager;
	
    /**
     * ログイン処理を行う
     * @param authenticationRequest
     * @return
     * @throws TechMatchException
     */
	@PostMapping(value="/login")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> login(@RequestBody TechMatchAuthenticationRequestModel authenticationRequest) throws TechMatchException{
		try{
			// ユーザIDとパスワードによる照合トークンを取得
			UsernamePasswordAuthenticationToken authReq=new UsernamePasswordAuthenticationToken(authenticationRequest.getUserId(), authenticationRequest.getPassword());	
			// 対象ユーザが存在するかチェックし認証を行う
			Authentication auth = authManager.authenticate(authReq);
			// 認証OKの場合は、認証結果をContextHolderに設定
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(auth);
		}catch(BadCredentialsException e) {
			List<String> el=new ArrayList<String>();
			el.add("認証情報が違います");
			throw new TechMatchAuthException(el);
		}
		try {
			// 認証に使用する情報を返す
			final UserDetails userDetails = techMatchUserDetailsService.loadUserByUsername(authenticationRequest.getUserId());
			final String jwtToken = jwtUtil.generateToken(userDetails);
		    logger.info("ユーザがログインしました "+authenticationRequest.getUserId());
			return ResponseEntity.ok(new TechMatchAuthenticationResponseModel(jwtToken));
		}catch(UsernameNotFoundException e) {
			throw new UsernameNotFoundException("そのユーザ情報は存在しません");
		}
	}
	
	/**
	 * トークンの期限が過ぎているか確認します
	 */	
	@PostMapping(value="/tokenTest")
	@ResponseStatus(HttpStatus.OK)
	public boolean login(@RequestBody String token) {
		return jwtUtil.isTokenExpired(token);
	}
	
	/**
	 * トークンからユーザIDを確認します
	 */	
	@PostMapping(value="/confirmUserId")
	public List<String> confirmUserIdFromToken(@RequestBody String token) {
		List<String> list = new ArrayList<>();
		list.add(jwtUtil.extractUsername(token));
		return list;
	}
}
