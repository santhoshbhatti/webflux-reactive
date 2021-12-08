package com.piedpiper.reactiveweb.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.piedpiper.reactiveweb.dto.MathTuple;
import com.piedpiper.reactiveweb.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {
	public Mono<Response> square(int num){
		
		return Mono.fromSupplier(() -> {
			return new Response(num * num);
		});
		
	}
	
	public Flux<Response> tables(int n){
		return Flux.range(1, 10)
				//.doOnNext(i -> Util.sleepSeconds(1)) blocking sleep
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(i -> System.out.println("Reactive Math Processing element: "+i))
				.map(i -> new Response(i * n));
	}
	
	public Flux<Response> tablesNonReactive(int n){
		//This is not a reactive code
		//as the whole list is created in one go and stuffed into a flux
		
		//Where as tables method is a reactive code where individual response
		//objects are emitted to pipeline once theya re made available by the publisher
		
		//So this non reactive iomplementation does not enjoy the same benifits of above method
		//Even if the subscriber sends a unsubscribe signal this code wont stop till all elements are 
		//processed!!!!!!!------this is one of the most important pitfalls that needs to be avoided
		//while writing the reactive code
		List<Response> list = IntStream.rangeClosed(1, 10)
		.mapToObj(i -> new Response(n * i))
		.collect(Collectors.toList());
		
		return Flux.fromIterable(list);
		
		
	}

	public Mono<Response> multiply(Mono<MathTuple> mathTuple) {
		return mathTuple.map(m -> new Response(m.getA() * m.getB()));
		
	}

	public Mono<Response> cube(Integer num) {
		return Mono.fromSupplier(() -> {
			return new Response(num * num * num);
		});
	}
}
