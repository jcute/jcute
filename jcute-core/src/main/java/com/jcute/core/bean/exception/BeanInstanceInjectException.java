package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanInstanceInjectException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanInstanceInjectException(){
		super();
	}

	public BeanInstanceInjectException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanInstanceInjectException(String message){
		super(message);
	}

	public BeanInstanceInjectException(Throwable cause){
		super(cause);
	}

}