package com.jks.pb.aspect;


import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jks.pb.annotation.LogRecord;
import com.jks.pb.factory.ParseFunctionFactory;
import com.jks.pb.function.IParseFunction;
import com.jks.pb.util.SpelUtil;



@Aspect
@Component
public class AccessLogAspect {

	@Autowired
	ParseFunctionFactory  parseFunctionfactory;
	
	@Autowired
	Map<String, IParseFunction> allFunctionMap=new HashMap<>();;


	@Pointcut("@annotation(com.jks.pb.annotation.LogRecord)")
	public void LogRecord() {

	}

	@Before("LogRecord()")
	public void doBefore(JoinPoint joinPoint) throws InterruptedException {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		LogRecord logrecord=signature.getMethod().getAnnotation(LogRecord.class);
		String spelContent =logrecord.content();
		
		
		String content=SpelUtil.generateKeyBySpEL(spelContent, joinPoint, parseFunctionfactory);
		System.out.println(content);
	}

	
	
	@AfterReturning(value = "LogRecord()", returning = "resp")
	public void after(JoinPoint joinPoint,Object resp) {
		
		
	}

	
	


}
