package com.piedpiper.reactiveweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.piedpiper.reactiveweb.service.RequestHandler;

@Configuration
public class FunctionalEndpointConfig {
	
	@Autowired
	RequestHandler requestHandler;
	
	@Bean
	public RouterFunction<ServerResponse> serverResponseRouterFunction(){
		return RouterFunctions.route()
				.GET("router/square/{input}",this.requestHandler::squareHandler)
				.GET("router/tables/{input}", requestHandler::tablesHandler)
				.GET("router/tables/{input}/stream", requestHandler::tablesStreamHandler)
				.POST("router/multiply",requestHandler::multiplyHandler)
				.build();
	}

}
