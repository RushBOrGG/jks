package com.jks.pb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
public class WebhookController {

	@RequestMapping("/github-webhook")
	public void hook(HttpServletRequest request,@RequestBody JSONObject  obj){
		System.out.println(2);
		System.out.println(1);
	}
}
