package com.piedpiper.reactiveweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piedpiper.reactiveweb.dto.Response;
import com.piedpiper.reactiveweb.service.MathService;

@RestController
@RequestMapping("math")
public class MathController {

	@Autowired
	private MathService mathService;
	
	@RequestMapping("square/{input}")
	public Response square(@PathVariable int input) {
		return mathService.square(input);
	}
	
	@RequestMapping("tables/{input}")
	public List<Response> tables(@PathVariable int input) {
		return mathService.tables(input);
	}
}


