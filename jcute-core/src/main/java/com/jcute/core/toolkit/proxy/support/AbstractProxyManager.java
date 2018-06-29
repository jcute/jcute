package com.jcute.core.toolkit.proxy.support;

import com.jcute.core.toolkit.proxy.ProxyHandler;
import com.jcute.core.toolkit.proxy.ProxyManager;

public abstract class AbstractProxyManager implements ProxyManager{

	@Override
	public <T>T createProxyByClass(Class<T> targetClass,ProxyHandler... handlers){
		return this.createProxyByClass(targetClass,null,null,handlers);
	}
	
}