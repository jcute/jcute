package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanInstanceDestoryException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanInstanceDestoryException(){
		super();
	}

	public BeanInstanceDestoryException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanInstanceDestoryException(String message){
		super(message);
	}

	public BeanInstanceDestoryException(Throwable cause){
		super(cause);
	}

}