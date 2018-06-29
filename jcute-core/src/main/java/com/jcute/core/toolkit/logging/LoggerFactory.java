package com.jcute.core.toolkit.logging;

import com.jcute.core.toolkit.logging.support.JdkLoggerFactory;
import com.jcute.core.toolkit.logging.support.Log4j2LoggerFactory;
import com.jcute.core.toolkit.logging.support.Log4jLoggerFactory;
import com.jcute.core.toolkit.logging.support.Slf4jLoggerFactory;

public abstract class LoggerFactory{

	private static volatile LoggerFactory defaultLoggerFactory;

	private static LoggerFactory createDefaultLoggerFactory(String factoryName){
		LoggerFactory loggerFactory = null;
		try{
			loggerFactory = new Slf4jLoggerFactory();
			loggerFactory.createLogger(factoryName).debug("Using SLF4J as the default logging framework");
		}catch(Throwable e){
			try{
				loggerFactory = new Log4jLoggerFactory();
				loggerFactory.createLogger(factoryName).debug("Using Log4J as the default logging framework");
			}catch(Throwable ee){
				try{
					loggerFactory = new Log4j2LoggerFactory();
					loggerFactory.createLogger(factoryName).debug("Using Log4J2 as the default logging framework");
				}catch(Throwable eee){
					loggerFactory = new JdkLoggerFactory();
					loggerFactory.createLogger(factoryName).debug("Using JdkLogger as the default logging framework");
				}
			}
		}
		return loggerFactory;
	}

	public static LoggerFactory getDefaultLoggerFactory(){
		if(null == defaultLoggerFactory){
			defaultLoggerFactory = createDefaultLoggerFactory(LoggerFactory.class.getName());
		}
		return defaultLoggerFactory;
	}

	public static void setDefaultLoggerFactory(LoggerFactory defaultLoggerFactory){
		if(null == defaultLoggerFactory){
			throw new NullPointerException("defaultLoggerFactory");
		}
		LoggerFactory.defaultLoggerFactory = defaultLoggerFactory;
	}

	public static Logger getLogger(String target){
		return getDefaultLoggerFactory().createLogger(target);
	}

	public static Logger getLogger(Class<?> target){
		return getLogger(target.getName());
	}

	protected abstract Logger createLogger(String loggerName);

}