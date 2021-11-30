package com.piedpiper.reactiveweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.piedpiper.reactiveweb.dto.MathTuple;
import com.piedpiper.reactiveweb.dto.Response;

import reactor.core.publisher.Mono;

@Service
public class RequestHandler {
	
	@Autowired
	private ReactiveMathService mathService;
	
	public Mono<ServerResponse> squareHandler(ServerRequest request){
		Integer num = Integer.parseInt(request.pathVariable("input"));
		var square = this.mathService.square(num);
		return ServerResponse.ok().body(square, Response.class);
	}
	
	public Mono<ServerResponse> tablesHandler(ServerRequest request){
		Integer num = Integer.parseInt(request.pathVariable("input"));
		var tables = this.mathService.tables(num);
		return ServerResponse.ok().body(tables, Response.class);
	}
	
	public Mono<ServerResponse> tablesStreamHandler(ServerRequest request){
		Integer num = Integer.parseInt(request.pathVariable("input"));
		var tables = this.mathService.tables(num);
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(tables, Response.class);
	}
	
	public Mono<ServerResponse> multiplyHandler(ServerRequest request){
		var mono = request.bodyToMono(MathTuple.class);
		var resp = this.mathService.multiply(mono);
		return ServerResponse.ok().body(resp, Response.class);
	}
}
