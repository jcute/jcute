package com.jcute.core.bean;

public class TestService{
	
	public String getName(){
		return "test";
	}
	
	public TestChildService childService(){
		return new TestChildService();
	}
	
}