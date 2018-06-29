package com.jcute.core.toolkit.proxy;

public interface ProxyFactory{

	public Object execute(ProxyChain proxyChain) throws Throwable;
	
}