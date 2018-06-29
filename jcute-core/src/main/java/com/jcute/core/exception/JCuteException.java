package com.jcute.core.exception;

public class JCuteException extends Exception{

	private static final long serialVersionUID = 1910275173070874596L;

	public JCuteException(){
		super();
	}

	public JCuteException(String message,Throwable cause){
		super(message,cause);
	}

	public JCuteException(String message){
		super(message);
	}

	public JCuteException(Throwable cause){
		super(cause);
	}

}