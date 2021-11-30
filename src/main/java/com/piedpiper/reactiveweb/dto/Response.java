package com.piedpiper.reactiveweb.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Response {
	
	private Date date;
	private int output;
	public Response(int output) {
		this.output = output;
		this.date = new Date();
	}
	
	

}
