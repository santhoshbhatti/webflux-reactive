package com.piedpiper.reactiveweb.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.piedpiper.reactiveweb.dto.Response;
import com.piedpiper.reactiveweb.utils.Util;

@Service
public class MathService {
	
	public Response square(int num) {
		return new Response(num * num);
	}
	
	public List<Response> tables(int num){
		return IntStream.rangeClosed(1, 10)
				.peek(i -> Util.sleepSeconds(1))
				.peek(i -> System.out.println("processing element: "+i))
				.mapToObj(i -> new Response(i * num))
				.collect(Collectors.toList());
	}
	
}
