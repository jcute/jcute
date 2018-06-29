package com.jcute.core.toolkit.logging.support;

import java.util.Enumeration;

import org.apache.log4j.LogManager;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public class Log4jLoggerFactory extends LoggerFactory{

	public Log4jLoggerFactory(){
		org.apache.log4j.Logger logger = LogManager.getRootLogger();
		if(logger != null){
			Enumeration<?> appenders = logger.getAllAppenders();
			if(appenders == null || appenders.hasMoreElements() == false){
				throw new RuntimeException("No Appenders");
			}
		}
	}

	@Override
	protected Logger createLogger(String loggerName){
		return new Log4jLogger(org.apache.log4j.Logger.getLogger(loggerName));
	}

}