package com.jcute.core.toolkit.logging;

public class LoggerTuple{

	private final String message;
	private final Throwable cause;

	public LoggerTuple(String message,Throwable cause){
		this.message = message;
		this.cause = cause;
	}

	public String getMessage(){
		return message;
	}

	public Throwable getCause(){
		return cause;
	}

}