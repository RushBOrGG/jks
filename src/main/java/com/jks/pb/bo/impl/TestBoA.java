package com.jks.pb.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jks.pb.bo.ITestBoA;
import com.jks.pb.dao.ITestDao;
import com.jks.pb.dao.TestDao;
import com.jks.pb.pojo.Inventor;
import com.jks.pb.pojo.QyUserInfo;

@Service
public class TestBoA implements ITestBoA{
	
	@Autowired
	ITestDao testDao;

	@Override
	public void function1(Inventor a) {
		// TODO 自动生成方法存根
		System.err.println("123");
		QyUserInfo user=new QyUserInfo();
		testDao.testdao(user);
	}

}
