package com.jcute.network.toolkit.future;

public interface Future<T>{
	
	public T getTarget();
	
	public boolean waitComplete();
	
	public boolean waitComplete(int timeout);
	
	public boolean isComplete();
	
	public boolean isSuccess();
	
	public boolean isFailure();
	
	public void setSuccess();
	
	public void setFailure();
	
	public void attachListener(FutureListener<T> listener);
	
	public void detachListener(FutureListener<T> listener);
	
}