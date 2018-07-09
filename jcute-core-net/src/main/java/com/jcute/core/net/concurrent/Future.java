package com.jcute.core.net.concurrent;

import com.jcute.core.net.session.Session;

public interface Future{
	
	public Session getSession();
	
	public boolean complete();
	
	public boolean complete(int timeout);
	
	public boolean isCompleted();
	
	public boolean isSuccess();
	
	public boolean isFailure();
	
	public void attachListener(FutureListener listener);
	
	public void detachListener(FutureListener listener);
	
	public void setSuccess();
	public void setFailure();
	
}