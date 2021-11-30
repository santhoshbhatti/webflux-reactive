package com.piedpiper.reactiveweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piedpiper.reactiveweb.dto.Response;
import com.piedpiper.reactiveweb.exceptions.InputValidatationException;
import com.piedpiper.reactiveweb.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReaciveMathValidationController {
	@Autowired
	private ReactiveMathService reactiveMathService;
	
	@GetMapping("square/{input}/throw")
	public Mono<Response> square(@PathVariable int input) {
		if(input < 10 || input > 20) {
			throw new InputValidatationException(input);
		}
		return reactiveMathService.square(input);
	}
	
	@GetMapping("square/{input}/mono-error")
	public Mono<Response> squareReturningMonoError(@PathVariable int input) {
		return Mono.just(input)
		.handle((i,sink) -> {
			if(i < 10 || i > 20) {
				sink.error(new InputValidatationException(input));
			}else {
			sink.next(i);
			}
		})
		.flatMap(i -> reactiveMathService.square(input));
		
	}
	
	@GetMapping("square/{input}/mono-badRequest")
	public Mono<ResponseEntity<Response>> squareReturningBadRequest(@PathVariable int input) {
		return Mono.just(input)
		.filter(i -> i >= 10 && i <= 20)
		.flatMap(i -> reactiveMathService.square(i))
		.map(ResponseEntity::ok)
		.defaultIfEmpty(ResponseEntity.badRequest().build());
		
	}
}
