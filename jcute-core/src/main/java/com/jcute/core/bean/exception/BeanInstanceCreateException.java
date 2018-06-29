package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanInstanceCreateException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanInstanceCreateException(){
		super();
	}

	public BeanInstanceCreateException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanInstanceCreateException(String message){
		super(message);
	}

	public BeanInstanceCreateException(Throwable cause){
		super(cause);
	}

}