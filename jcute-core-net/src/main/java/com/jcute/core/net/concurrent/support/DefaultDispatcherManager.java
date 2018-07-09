package com.jcute.core.net.concurrent.support;

import com.jcute.core.net.concurrent.Dispatcher;
import com.jcute.core.net.concurrent.DispatcherManager;

public class DefaultDispatcherManager implements DispatcherManager{

	@Override
	public Dispatcher getDispatcher(){
		return new DefaultDispatcher();
	}
	
}