package com.piedpiper.reactiveweb.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InputFailedValidationResponse {

	private int erroCode;
	private int input;
	private String message;
	
}
