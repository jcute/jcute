package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanDefinitionNotFoundException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanDefinitionNotFoundException(){
		super();
	}

	public BeanDefinitionNotFoundException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanDefinitionNotFoundException(String message){
		super(message);
	}

	public BeanDefinitionNotFoundException(Throwable cause){
		super(cause);
	}

}