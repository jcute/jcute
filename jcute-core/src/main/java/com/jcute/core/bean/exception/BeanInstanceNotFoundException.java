package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanInstanceNotFoundException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanInstanceNotFoundException(){
		super();
	}

	public BeanInstanceNotFoundException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanInstanceNotFoundException(String message){
		super(message);
	}

	public BeanInstanceNotFoundException(Throwable cause){
		super(cause);
	}

}