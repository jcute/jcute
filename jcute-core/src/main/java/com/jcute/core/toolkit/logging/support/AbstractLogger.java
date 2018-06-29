package com.jcute.core.toolkit.logging.support;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerLevel;

public abstract class AbstractLogger implements Logger{

	private static final long serialVersionUID = 1507318548184347377L;
	private static final String EXCEPTION_MESSAGE = "Unexpected Exception:";

	private final String name;

	protected AbstractLogger(String name){
		if(null == name){
			throw new NullPointerException("name");
		}
		this.name = name;
	}

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public boolean isEnabled(LoggerLevel level){
		if(null == level){
			throw new Error();
		}
		if(level == LoggerLevel.TRACE){
			return this.isTraceEnabled();
		}else if(level == LoggerLevel.DEBUG){
			return this.isDebugEnabled();
		}else if(level == LoggerLevel.INFO){
			return this.isInfoEnabled();
		}else if(level == LoggerLevel.WARN){
			return this.isWarnEnabled();
		}else if(level == LoggerLevel.ERROR){
			return this.isErrorEnabled();
		}
		return false;
	}

	@Override
	public void trace(Throwable cause){
		this.trace(EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void debug(Throwable cause){
		this.debug(EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void info(Throwable cause){
		this.info(EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void warn(Throwable cause){
		this.warn(EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void error(Throwable cause){
		this.error(EXCEPTION_MESSAGE,cause);
	}

	@Override
	public void log(LoggerLevel level,String message,Object... arguments){
		if(null == level){
			throw new Error();
		}
		if(level == LoggerLevel.TRACE){
			this.trace(message,arguments);
		}else if(level == LoggerLevel.DEBUG){
			this.debug(message,arguments);
		}else if(level == LoggerLevel.INFO){
			this.info(message,arguments);
		}else if(level == LoggerLevel.WARN){
			this.warn(message,arguments);
		}else if(level == LoggerLevel.ERROR){
			this.error(message,arguments);
		}
	}

	@Override
	public void log(LoggerLevel level,String message,Throwable cause){
		if(null == level){
			throw new Error();
		}
		if(level == LoggerLevel.TRACE){
			this.trace(message,cause);
		}else if(level == LoggerLevel.DEBUG){
			this.debug(message,cause);
		}else if(level == LoggerLevel.INFO){
			this.info(message,cause);
		}else if(level == LoggerLevel.WARN){
			this.warn(message,cause);
		}else if(level == LoggerLevel.ERROR){
			this.error(message,cause);
		}
	}

	@Override
	public void log(LoggerLevel level,Throwable cause){
		if(null == level){
			throw new Error();
		}
		if(level == LoggerLevel.TRACE){
			this.trace(cause);
		}else if(level == LoggerLevel.DEBUG){
			this.debug(cause);
		}else if(level == LoggerLevel.INFO){
			this.info(cause);
		}else if(level == LoggerLevel.WARN){
			this.warn(cause);
		}else if(level == LoggerLevel.ERROR){
			this.error(cause);
		}
	}

}