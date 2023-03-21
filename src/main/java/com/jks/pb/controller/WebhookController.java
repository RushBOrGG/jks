package com.jks.pb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

	@RequestMapping("/github-webhook")
	public void hook(HttpServletRequest request){
		
		System.out.println(1);
	}
}
