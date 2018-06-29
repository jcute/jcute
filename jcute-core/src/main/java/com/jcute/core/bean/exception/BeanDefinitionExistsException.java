package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanDefinitionExistsException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanDefinitionExistsException(){
		super();
	}

	public BeanDefinitionExistsException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanDefinitionExistsException(String message){
		super(message);
	}

	public BeanDefinitionExistsException(Throwable cause){
		super(cause);
	}

}