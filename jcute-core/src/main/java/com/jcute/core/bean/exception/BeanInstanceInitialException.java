package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanInstanceInitialException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanInstanceInitialException(){
		super();
	}

	public BeanInstanceInitialException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanInstanceInitialException(String message){
		super(message);
	}

	public BeanInstanceInitialException(Throwable cause){
		super(cause);
	}

}