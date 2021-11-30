package com.piedpiper.reactiveweb.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.piedpiper.reactiveweb.dto.InputFailedValidationResponse;
import com.piedpiper.reactiveweb.exceptions.InputValidatationException;

@ControllerAdvice
public class InputValidationHandler {
	
	@ExceptionHandler(InputValidatationException.class)
	public ResponseEntity<InputFailedValidationResponse> handleInputValidationException(InputValidatationException exception){
		var exRes = new InputFailedValidationResponse();
		exRes.setErroCode(exception.getErrorCode());
		exRes.setMessage(exception.getMessage());
		exRes.setInput(exception.getInput());
		
		return ResponseEntity.badRequest().body(exRes);
	}
}
