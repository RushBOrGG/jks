package com.jks.pb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ilsmanage")
public class Tcontroller {

	@GetMapping("/findAllKey")
	public String test(){
		return "1";
		
	}
	
	
	
	@GetMapping("/findAllKey2")
	public String test2(){
		return "1";
		
	}
}
