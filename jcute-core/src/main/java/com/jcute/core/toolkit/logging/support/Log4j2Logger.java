package com.jcute.core.toolkit.logging.support;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerLevel;

public class Log4j2Logger extends ExtendedLoggerWrapper implements Logger{

	private static final long serialVersionUID = 3690559976345512537L;
	private static final String EXCEPTION_MESSAGE = "Unexpected Exception:";

	public Log4j2Logger(org.apache.logging.log4j.Logger logger){
		super((ExtendedLogger)logger,logger.getName(),logger.getMessageFactory());
	}

	@Override
	public boolean isEnabled(LoggerLevel level){
		return this.isEnabled(this.toLevel(level));
	}

	@Override
	public void trace(Throwable cause){
		this.log(Level.TRACE,EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void debug(Throwable cause){
		this.log(Level.DEBUG,EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void info(Throwable cause){
		this.log(Level.INFO,EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void warn(Throwable cause){
		this.log(Level.WARN,EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void error(Throwable cause){
		this.log(Level.ERROR,EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void log(LoggerLevel level,String message,Object... arguments){
		this.log(this.toLevel(level),message,arguments);
	}

	@Override
	public void log(LoggerLevel level,String message,Throwable cause){
		this.log(this.toLevel(level),message,cause);
	}

	@Override
	public void log(LoggerLevel level,Throwable cause){
		this.log(this.toLevel(level),EXCEPTION_MESSAGE,cause);
	}

	private Level toLevel(LoggerLevel level){
		if(null == level){
			throw new Error();
		}
		if(level == LoggerLevel.TRACE){
			return Level.TRACE;
		}else if(level == LoggerLevel.DEBUG){
			return Level.DEBUG;
		}else if(level == LoggerLevel.INFO){
			return Level.INFO;
		}else if(level == LoggerLevel.WARN){
			return Level.WARN;
		}else if(level == LoggerLevel.ERROR){
			return Level.ERROR;
		}
		throw new Error();
	}

}