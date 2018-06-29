package com.jcute.core.toolkit.proxy;

import com.jcute.core.toolkit.proxy.support.ProxyHandlerForInterface;

public interface ProxyManager{
	
	public <T> T createProxyByClass(Class<T> targetClass,Class<?>[] targetParameterTypes,Object[] targetParameterDatas,ProxyHandler...handlers); 
	
	public <T> T createProxyByClass(Class<T> targetClass,ProxyHandler...handlers); 
	
	public <T> T createProxyByInstance(T instance,ProxyHandler...handlers);
	
	public <T> T createProxyByInterface(Class<T> targetClass,ProxyHandlerForInterface...handlers);
	
	public boolean isProxy(Object targetInstance);
	
}