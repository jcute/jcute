package com.jcute.core.toolkit.proxy.support;

import com.jcute.core.toolkit.proxy.ProxyHandler;

public abstract class ProxyHandlerForInterface implements ProxyHandler{

	@Override
	public abstract ProxyFactoryForInterface getFactory();
	
}