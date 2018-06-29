package com.jcute.core.toolkit.logging;

import java.io.Serializable;

public interface Logger extends Serializable{

	public String getName();

	public boolean isEnabled(LoggerLevel level);

	public boolean isTraceEnabled();

	public boolean isDebugEnabled();

	public boolean isInfoEnabled();

	public boolean isWarnEnabled();

	public boolean isErrorEnabled();

	public void trace(String message,Object... arguments);

	public void trace(String message,Throwable cause);

	public void trace(Throwable cause);

	public void debug(String message,Object... arguments);

	public void debug(String message,Throwable cause);

	public void debug(Throwable cause);

	public void info(String message,Object... arguments);

	public void info(String message,Throwable cause);

	public void info(Throwable cause);

	public void warn(String message,Object... arguments);

	public void warn(String message,Throwable cause);

	public void warn(Throwable cause);

	public void error(String message,Object... arguments);

	public void error(String message,Throwable cause);

	public void error(Throwable cause);

	public void log(LoggerLevel level,String message,Object... arguments);

	public void log(LoggerLevel level,String message,Throwable cause);

	public void log(LoggerLevel level,Throwable cause);
	
}