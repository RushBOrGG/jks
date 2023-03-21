package com.jks.pb.bo.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jks.pb.bo.ITestBoA;
import com.jks.pb.bo.ITestBoB;
import com.jks.pb.pojo.Inventor;
import com.jks.pb.util.TestUtils;

@Service
public class TestBoB implements ITestBoB{
	
	
	@Autowired
	ITestBoA iTestBoA;

	@Override
	public void function2(Inventor a) {
		this.function3();
		iTestBoA.function1(a);
		TestUtils.testUtils();
		
	}

	@Override
	public void function3() {
		// TODO 自动生成方法存根
		
	}

}
