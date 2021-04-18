package com.techmatch.base.common.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nimbusds.oauth2.sdk.ErrorObject;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.techmatch.base.exception.TechMatchAuthException;
import com.techmatch.base.exception.TechMatchException;
import com.techmatch.base.exception.TechMatchRequirementException;
import com.techmatch.base.exception.TechMatchValidationException;

/**
 * エラーレスポンスをハンドルするクラスです
 * @author firmy
 *
 */
@RestControllerAdvice
public class BadRequestExceptionHandler extends ResponseEntityExceptionHandler{
	
	// バリデーションエラーの例外を捕捉し、クライアントに返します
    @ExceptionHandler(value=TechMatchValidationException.class)
    public ResponseEntity<Map<String,String>> getException(TechMatchValidationException e){
    	return new ResponseEntity<Map<String,String>>(e.getErrorMessages(),HttpStatus.BAD_REQUEST);
    }

	// 認証の例外を捕捉し、クライアントに返します
    @ExceptionHandler(value=TechMatchAuthException.class)
    public ResponseEntity<List<String>> getAuthException(TechMatchAuthException e){
    	return new ResponseEntity<List<String>>(e.getErrorMessages(),HttpStatus.BAD_REQUEST);
    }
    // 募集情報に関する例外を捕捉し、クライアントに返します
    @ExceptionHandler(value=TechMatchRequirementException.class)
    public ResponseEntity<String> returnRequirementException(TechMatchRequirementException e){
    	return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
