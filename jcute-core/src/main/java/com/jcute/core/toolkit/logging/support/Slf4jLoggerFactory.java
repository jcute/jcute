package com.jcute.core.toolkit.logging.support;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public class Slf4jLoggerFactory extends LoggerFactory{

	public Slf4jLoggerFactory(){
		try{
			Slf4jLoggerFactory.class.getClassLoader().loadClass("org.slf4j.impl.StaticLoggerBinder");
		}catch(ClassNotFoundException e){
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected Logger createLogger(String loggerName){
		return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(loggerName));
	}

}