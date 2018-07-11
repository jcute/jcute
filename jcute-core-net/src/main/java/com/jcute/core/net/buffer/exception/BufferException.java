package com.jcute.core.net.buffer.exception;

public class BufferException extends RuntimeException{

	private static final long serialVersionUID = 3905954714299688001L;

	public BufferException(){
		super();
	}

	public BufferException(String message,Throwable cause){
		super(message,cause);
	}

	public BufferException(String message){
		super(message);
	}

	public BufferException(Throwable cause){
		super(cause);
	}

}