package com.jcute.core.util;

public class GenericUtils{
	
	@SuppressWarnings("unchecked")
	public static <T> T parse(Object instance){
		return (T)instance;
	}
	
}