package com.jcute.core.toolkit.proxy.support;

import java.lang.reflect.Method;

import com.jcute.core.toolkit.proxy.ProxyMethod;

public abstract class AbstractProxyMethod implements ProxyMethod{

	protected Method targetMethod;
	
	public AbstractProxyMethod(Method targetMethod){
		if(null == targetMethod){
			throw new IllegalArgumentException("target method must not be null");
		}
		this.targetMethod = targetMethod;
	}

	@Override
	public Method getTargetMethod(){
		return this.targetMethod;
	}
	
	@Override
	public Object invokeTargetMethod(Object targetInstance) throws Throwable{
		return this.invokeTargetMethod(targetInstance,null);
	}

}