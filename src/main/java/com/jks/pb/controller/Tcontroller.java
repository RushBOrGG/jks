package com.jks.pb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jks.pb.annotation.LogRecord;
import com.jks.pb.bo.ITestBoA;
import com.jks.pb.bo.ITestBoB;
import com.jks.pb.pojo.Inventor;
import com.jks.pb.util.TestUtils;

@RestController
@RequestMapping("/ilsmanage")
public class Tcontroller {
	
	@Autowired
	ITestBoA testBoA;
	
	@Autowired
	ITestBoB testBoB;

	@GetMapping("/findAllKey")
	public String test(){
		return "1";
		
	}
	
	
	@LogRecord(content = "恭喜！#{@{getName(#entity.id)}}同学今天已经#{@{getName(#entity.nationality)}}累计打卡#{#entity.getContinuationNum()}，连续打卡#{#entity.getTotalNum()}")
	@GetMapping("/findAllKey2")
	public String test2(Inventor entity ){
		return "1";
		
	}
	
	
	
	@GetMapping("/con1")
	public String con1(Inventor entity ){
		//testBoA.function1();
		return "1";
		
	}
	
	
	@GetMapping("/con2")
	public String con2(Inventor entity ){
		TestUtils.testUtils();
		testBoB.function2(new Inventor());
		return "1";
		
	}
}
