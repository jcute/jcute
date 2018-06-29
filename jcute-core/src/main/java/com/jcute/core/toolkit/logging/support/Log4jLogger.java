package com.jcute.core.toolkit.logging.support;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.jcute.core.toolkit.logging.LoggerFormatter;
import com.jcute.core.toolkit.logging.LoggerTuple;

public class Log4jLogger extends AbstractLogger{

	private static final long serialVersionUID = 5222621914283201713L;
	private static final String FQCN = Log4jLogger.class.getName();

	private final transient Logger logger;
	private final transient boolean traceCapable;

	public Log4jLogger(Logger logger){
		super(logger.getName());
		this.logger = logger;
		this.traceCapable = this.isTraceCapable();
	}

	private boolean isTraceCapable(){
		try{
			this.logger.isTraceEnabled();
			return true;
		}catch(NoSuchMethodError e){
			return false;
		}
	}

	@Override
	public boolean isTraceEnabled(){
		if(this.traceCapable){
			return this.logger.isTraceEnabled();
		}else{
			return this.logger.isDebugEnabled();
		}
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
		return this.logger.isEnabledFor(Level.WARN);
	}

	@Override
	public boolean isErrorEnabled(){
		return this.logger.isEnabledFor(Level.ERROR);
	}

	@Override
	public void trace(String message,Object... arguments){
		if(this.isTraceEnabled()){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.logger.log(FQCN,this.traceCapable ? Level.TRACE : Level.DEBUG,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void trace(String message,Throwable cause){
		this.logger.log(FQCN,this.traceCapable ? Level.TRACE : Level.DEBUG,message,cause);
	}

	@Override
	public void debug(String message,Object... arguments){
		if(this.isDebugEnabled()){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.logger.log(FQCN,Level.DEBUG,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void debug(String message,Throwable cause){
		this.logger.log(FQCN,Level.DEBUG,message,cause);
	}

	@Override
	public void info(String message,Object... arguments){
		if(this.isInfoEnabled()){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.logger.log(FQCN,Level.INFO,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void info(String message,Throwable cause){
		this.logger.log(FQCN,Level.INFO,message,cause);
	}

	@Override
	public void warn(String message,Object... arguments){
		if(this.isWarnEnabled()){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.logger.log(FQCN,Level.WARN,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void warn(String message,Throwable cause){
		this.logger.log(FQCN,Level.WARN,message,cause);
	}

	@Override
	public void error(String message,Object... arguments){
		if(this.isErrorEnabled()){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.logger.log(FQCN,Level.ERROR,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void error(String message,Throwable cause){
		this.logger.log(FQCN,Level.ERROR,message,cause);
	}

}