package com.piedpiper.reactiveweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.piedpiper.reactiveweb.dto.InputFailedValidationResponse;
import com.piedpiper.reactiveweb.dto.MathTuple;
import com.piedpiper.reactiveweb.dto.Response;
import com.piedpiper.reactiveweb.exceptions.InputValidatationException;

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
	
	public Mono<ServerResponse> cubeHandler(ServerRequest request){
		Integer num = Integer.parseInt(request.pathVariable("input"));
		var square = this.mathService.cube(num);
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
	
	public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest request){
		Integer num = Integer.parseInt(request.pathVariable("input"));
		//in this approach we handle the validation error 
		//manually. By populating the ServerResponse with 
		//a error bean and a status of bad request.
		//This works but the problem is spring helps us in handling all such exceptions
		//in an aspect orented manner
		if(num < 10 || num > 20) {
			InputFailedValidationResponse resp = new InputFailedValidationResponse();
			return ServerResponse.badRequest().bodyValue(resp);
		}
		var square = this.mathService.square(num);
		return ServerResponse.ok().body(square, Response.class);
	}
	
	public Mono<ServerResponse> squareHandlerWithValidationException(ServerRequest request){
		Integer num = Integer.parseInt(request.pathVariable("input"));
		
		//This is an implementation which shos how spring helps us in handling all such exceptions
		//in an aspect orented manner
		
		//Now we also need to inform the router how to hndle InputValidatationException
		
		//So wherever  such a Exception is emitted the exception handler code will
		//automatically be invoked.
		if(num < 10 || num > 20) {
			var exception = new InputValidatationException(num);
			return Mono.error(exception);
			
		}
		var square = this.mathService.square(num);
		return ServerResponse.ok().body(square, Response.class);
	}
}
