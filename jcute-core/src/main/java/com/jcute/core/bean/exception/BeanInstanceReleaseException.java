package com.jcute.core.bean.exception;

import com.jcute.core.exception.JCuteException;

public class BeanInstanceReleaseException extends JCuteException{

	private static final long serialVersionUID = -3285248781726244962L;

	public BeanInstanceReleaseException(){
		super();
	}

	public BeanInstanceReleaseException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanInstanceReleaseException(String message){
		super(message);
	}

	public BeanInstanceReleaseException(Throwable cause){
		super(cause);
	}

}