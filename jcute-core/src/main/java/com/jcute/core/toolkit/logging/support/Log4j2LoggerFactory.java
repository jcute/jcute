package com.jcute.core.toolkit.logging.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.util.ProviderUtil;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public class Log4j2LoggerFactory extends LoggerFactory{
	
	public Log4j2LoggerFactory(){
		if(ProviderUtil.hasProviders() == false){
			throw new RuntimeException("Missing Providers");
		}
	}
	
	@Override
	protected Logger createLogger(String loggerName){
		return new Log4j2Logger(LogManager.getLogger(loggerName));
	}

}