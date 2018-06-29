package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanDefinitionMultipleException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanDefinitionMultipleException(){
		super();
	}

	public BeanDefinitionMultipleException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanDefinitionMultipleException(String message){
		super(message);
	}

	public BeanDefinitionMultipleException(Throwable cause){
		super(cause);
	}

}