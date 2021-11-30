package com.piedpiper.reactiveweb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piedpiper.reactiveweb.dto.MathTuple;
import com.piedpiper.reactiveweb.dto.Response;
import com.piedpiper.reactiveweb.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
	@Autowired
	private ReactiveMathService reactiveMathService;
	
	@RequestMapping("square/{input}")
	public Mono<Response> square(@PathVariable int input) {
		return reactiveMathService.square(input);
	}
	
	@RequestMapping("tables/{input}")
	public Flux<Response> tables(@PathVariable int input) {
		return reactiveMathService.tables(input);
	}
	
	@GetMapping(path="tables/{input}/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> streamTables(@PathVariable int input) {
		return reactiveMathService.tables(input);
	}
	
	@PostMapping(path = "/multiply")
	public Mono<Response> multiply(@RequestBody Mono<MathTuple> mathTuple,
			@RequestHeader Map<String, String> headers){
		System.out.println("headers: "+headers);
		return reactiveMathService.multiply(mathTuple);
	}
}
