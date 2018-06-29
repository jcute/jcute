package com.jcute.core.toolkit.logging.support;

import org.slf4j.Logger;

public class Slf4jLogger extends AbstractLogger{

	private static final long serialVersionUID = 1641910518230162037L;
	
	private final transient Logger logger;

	public Slf4jLogger(Logger logger){
		super(logger.getName());
		this.logger = logger;
	}

	@Override
	public boolean isTraceEnabled(){
		return this.logger.isTraceEnabled();
	}

	@Override
	public boolean isDebugEnabled(){
		return this.logger.isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled(){
		return this.logger.isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled(){
		return this.logger.isWarnEnabled();
	}

	@Override
	public boolean isErrorEnabled(){
		return this.logger.isErrorEnabled();
	}

	@Override
	public void trace(String message,Object... arguments){
		this.logger.trace(message,arguments);
	}

	@Override
	public void trace(String message,Throwable cause){
		this.logger.trace(message,cause);
	}

	@Override
	public void debug(String message,Object... arguments){
		this.logger.debug(message,arguments);
	}

	@Override
	public void debug(String message,Throwable cause){
		this.logger.debug(message,cause);
	}

	@Override
	public void info(String message,Object... arguments){
		this.logger.info(message,arguments);
	}

	@Override
	public void info(String message,Throwable cause){
		this.logger.info(message,cause);
	}

	@Override
	public void warn(String message,Object... arguments){
		this.logger.warn(message,arguments);
	}

	@Override
	public void warn(String message,Throwable cause){
		this.logger.warn(message,cause);
	}

	@Override
	public void error(String message,Object... arguments){
		this.logger.error(message,arguments);
	}

	@Override
	public void error(String message,Throwable cause){
		this.logger.error(message,cause);
	}

}