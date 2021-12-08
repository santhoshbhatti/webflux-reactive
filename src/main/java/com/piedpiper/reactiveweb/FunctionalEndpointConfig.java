package com.piedpiper.reactiveweb;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.piedpiper.reactiveweb.dto.InputFailedValidationResponse;
import com.piedpiper.reactiveweb.exceptions.InputValidatationException;
import com.piedpiper.reactiveweb.service.CalculatorService;
import com.piedpiper.reactiveweb.service.RequestHandler;

import reactor.core.publisher.Mono;

@Configuration
public class FunctionalEndpointConfig {
	
	@Autowired
	RequestHandler requestHandler;
	
	@Autowired
	CalculatorService calcService;
	//Path based routing for writing more modular code
	//Here we can seggregate the root paths in an application
	//and provide router functions for each path's subpaths
	//Example here We are definig a router function for the path
	//"router" and providing a method reference to it
	//for all subpaths of the "router" 
	@Bean
	public RouterFunction<ServerResponse> moduleRouter(){
		return RouterFunctions.route()
				.path("orders", this::orderRouterFunction)
				.path("router", this::serverResponseRouterFunction)
				.build();
	}
	private RouterFunction<ServerResponse> orderRouterFunction(){
		 return RouterFunctions.route()
				.GET("cube/{input}",this.requestHandler::cubeHandler)
				.build();
	}
	private RouterFunction<ServerResponse> serverResponseRouterFunction(){
		return RouterFunctions.route()
				//.GET("square/{input}",this.requestHandler::squareHandler)
				//the handler resolution happens based on the path as well as the requestPredicate
				// defined below. Here we are saying that  any request submitted on square/{input}
				//should also have the {input} value starting with 1 and followed
				//by any number from 1 to 9
				//to resolve to handler method "squareHandler"
				.GET("square/{input}",RequestPredicates.path("*/1?") ,this.requestHandler::squareHandler)
				.GET("square/{input}",req -> ServerResponse.badRequest().bodyValue("only 10-20 allowed"))
				.GET("tables/{input}", requestHandler::tablesHandler)
				.GET("tables/{input}/stream", requestHandler::tablesStreamHandler)
				.POST("multiply",requestHandler::multiplyHandler)
				.GET("square/{input}/valid",this.requestHandler::squareHandlerWithValidationException)
				.onError(InputValidatationException.class, handleValidationError())
				.GET("calc/{a}/{b}",
						RequestPredicates
						.headers(headers -> headers.header("OP").get(0).equals("+")),
						calcService::add
						)
				.GET("calc/{a}/{b}",
						RequestPredicates
						.headers(headers -> headers.header("OP").get(0).equals("*")),
						calcService::multiply
						)
				.GET("calc/{a}/{b}",
						RequestPredicates
						.headers(headers -> headers.header("OP").get(0).equals("-")),
						calcService::substract
						)
				.GET("calc/{a}/{b}",
						RequestPredicates
						.headers(headers -> headers.header("OP").get(0).equals("/")),
						calcService::divide
						)
				.build();
	}
	
	/*@Bean
	public RouterFunction<ServerResponse> serverResponseRouterFunction(){
		return RouterFunctions.route()
				.GET("router/square/{input}",this.requestHandler::squareHandler)
				.GET("router/tables/{input}", requestHandler::tablesHandler)
				.GET("router/tables/{input}/stream", requestHandler::tablesStreamHandler)
				.POST("router/multiply",requestHandler::multiplyHandler)
				.GET("router/square/{input}/valid",this.requestHandler::squareHandlerWithValidationException)
				.onError(InputValidatationException.class, handleValidationError())
				.build();
	}*/

	private BiFunction<InputValidatationException, ServerRequest, Mono<ServerResponse>> handleValidationError() {
		// TODO Auto-generated method stub
		return (ex,req)->{
			var res = new InputFailedValidationResponse();
			res.setInput(Integer.parseInt(req.pathVariable("input")));
			res.setErroCode(400);
			res.setMessage(ex.getMessage());
			return ServerResponse.badRequest().bodyValue(res);
		};
	}

}
