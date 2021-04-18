package com.techmatch.base.common.error;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.techmatch.base.exception.TechMatchAuthException;

/**
 * エラーレスポンスをPOJO
 * @author firmy
 *
 */
public class ErrorResponse {
    private String message;
    private Map<String,String> messages;
    
    public ErrorResponse(String message){
        this.message = message;
    }
    
    public ErrorResponse(Map<String,String> messages){
        this.messages = messages;
    }
    
    public ErrorResponse(String message,Map<String,String> messages){
        this.message = message;
        this.messages=messages;
    }
    
    public ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status) {
    	return new ResponseEntity<ErrorResponse>(this,status);
    }
}
