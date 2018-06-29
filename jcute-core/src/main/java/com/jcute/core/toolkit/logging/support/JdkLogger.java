package com.jcute.core.toolkit.logging.support;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.jcute.core.toolkit.logging.LoggerFormatter;
import com.jcute.core.toolkit.logging.LoggerTuple;

public class JdkLogger extends AbstractLogger{

	private static final long serialVersionUID = 90648935125296995L;
	private static final String SELF = JdkLogger.class.getName();
	private static final String SUPER = AbstractLogger.class.getName();

	private final transient Logger logger;

	public JdkLogger(Logger logger){
		super(logger.getName());
		this.logger = logger;
	}

	@Override
	public boolean isTraceEnabled(){
		return this.logger.isLoggable(Level.FINEST);
	}

	@Override
	public boolean isDebugEnabled(){
		return this.logger.isLoggable(Level.FINE);
	}

	@Override
	public boolean isInfoEnabled(){
		return this.logger.isLoggable(Level.INFO);
	}

	@Override
	public boolean isWarnEnabled(){
		return this.logger.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isErrorEnabled(){
		return this.logger.isLoggable(Level.SEVERE);
	}

	@Override
	public void trace(String message,Object... arguments){
		if(this.logger.isLoggable(Level.FINEST)){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.log(SELF,Level.FINEST,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void trace(String message,Throwable cause){
		if(this.logger.isLoggable(Level.FINEST)){
			this.log(SELF,Level.FINEST,message,cause);
		}
	}

	@Override
	public void debug(String message,Object... arguments){
		if(this.logger.isLoggable(Level.FINE)){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.log(SELF,Level.FINE,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void debug(String message,Throwable cause){
		if(this.logger.isLoggable(Level.FINE)){
			this.log(SELF,Level.FINE,message,cause);
		}
	}

	@Override
	public void info(String message,Object... arguments){
		if(this.logger.isLoggable(Level.INFO)){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.log(SELF,Level.INFO,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void info(String message,Throwable cause){
		if(this.logger.isLoggable(Level.INFO)){
			this.log(SELF,Level.INFO,message,cause);
		}
	}

	@Override
	public void warn(String message,Object... arguments){
		if(this.logger.isLoggable(Level.WARNING)){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.log(SELF,Level.WARNING,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void warn(String message,Throwable cause){
		if(this.logger.isLoggable(Level.WARNING)){
			this.log(SELF,Level.WARNING,message,cause);
		}
	}

	@Override
	public void error(String message,Object... arguments){
		if(this.logger.isLoggable(Level.SEVERE)){
			LoggerTuple loggerTuple = LoggerFormatter.arrayFormat(message,arguments);
			this.log(SELF,Level.SEVERE,loggerTuple.getMessage(),loggerTuple.getCause());
		}
	}

	@Override
	public void error(String message,Throwable cause){
		if(this.logger.isLoggable(Level.SEVERE)){
			this.log(SELF,Level.SEVERE,message,cause);
		}
	}

	private void log(String callerFQCN,Level level,String message,Throwable cause){
		LogRecord record = new LogRecord(level,message);
		record.setLoggerName(this.getName());
		record.setThrown(cause);
		fillCallerData(callerFQCN,record);
		this.logger.log(record);
	}

	private static void fillCallerData(String callerFQCN,LogRecord record){
		StackTraceElement[] steArray = new Throwable().getStackTrace();
		int selfIndex = -1;
		for(int i = 0;i < steArray.length;i++){
			final String className = steArray[i].getClassName();
			if(className.equals(callerFQCN) || className.equals(SUPER)){
				selfIndex = i;
				break;
			}
		}
		int found = -1;
		for(int i = selfIndex + 1;i < steArray.length;i++){
			final String className = steArray[i].getClassName();
			if(!(className.equals(callerFQCN) || className.equals(SUPER))){
				found = i;
				break;
			}
		}
		if(found != -1){
			StackTraceElement ste = steArray[found];
			record.setSourceClassName(ste.getClassName());
			record.setSourceMethodName(ste.getMethodName());
		}
	}

}