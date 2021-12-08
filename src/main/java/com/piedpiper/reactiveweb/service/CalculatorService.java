package com.piedpiper.reactiveweb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
public class CalculatorService {
	
	public Mono<ServerResponse> add(ServerRequest request){
		var a = Integer.parseInt(request.pathVariable("a"));
		var b = Integer.parseInt(request.pathVariable("b"));
		return ServerResponse.ok().bodyValue(a + b);
	}
	
	public Mono<ServerResponse> multiply(ServerRequest request){
		var a = Integer.parseInt(request.pathVariable("a"));
		var b = Integer.parseInt(request.pathVariable("b"));
		return ServerResponse.ok().bodyValue(a * b);
	}
	
	public Mono<ServerResponse> substract(ServerRequest request){
		var a = Integer.parseInt(request.pathVariable("a"));
		var b = Integer.parseInt(request.pathVariable("b"));
		return ServerResponse.ok().bodyValue(a - b);
	}
	
	public Mono<ServerResponse> divide(ServerRequest request){
		var a = Integer.parseInt(request.pathVariable("a"));
		var b = Integer.parseInt(request.pathVariable("b"));
		return ServerResponse.ok().bodyValue(a / b);
	}

}
