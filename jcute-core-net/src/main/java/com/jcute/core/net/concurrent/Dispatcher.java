package com.jcute.core.net.concurrent;

import com.jcute.core.net.session.Session;

public interface Dispatcher{
	
	public void block();
	
	public void dispatch(Session session,Runnable runner);
	
}