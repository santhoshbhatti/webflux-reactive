package com.piedpiper.reactiveweb.exceptions;

public class InputValidatationException extends RuntimeException {
	private static final String MSG = "allowed range is 10 - 20";
	private static final int ERROR_CODE = 100;
	private final int input;
	public InputValidatationException(int input) {
		super(MSG);
		this.input = input;
	}
	public static int getErrorCode() {
		return ERROR_CODE;
	}
	public int getInput() {
		return input;
	}
	
	
}
