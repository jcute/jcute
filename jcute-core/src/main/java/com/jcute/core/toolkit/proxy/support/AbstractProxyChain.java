package com.jcute.core.toolkit.proxy.support;

import java.lang.reflect.Method;

import com.jcute.core.toolkit.proxy.ProxyChain;
import com.jcute.core.toolkit.proxy.ProxyHandler;
import com.jcute.core.toolkit.proxy.ProxyMethod;

public abstract class AbstractProxyChain implements ProxyChain{

	protected Class<?> targetClass;
	protected Object targetInstance;
	protected Method targetMethod;
	protected Object[] targetParameterDatas;
	protected ProxyMethod targetProxyMethod;
	protected ProxyHandler[] targetProxyHandlers;

	public AbstractProxyChain(Class<?> targetClass,Object targetInstance,Object[] targetParameterDatas,ProxyMethod targetProxyMethod,ProxyHandler[] targetProxyHandlers){
		if(null == targetClass){
			throw new IllegalArgumentException("target class must not be null");
		}
		if(null == targetInstance){
			throw new IllegalArgumentException("target instance must not be null");
		}
		if(null == targetProxyMethod){
			throw new IllegalArgumentException("target proxy method must not be null");
		}
		if(null == targetProxyMethod.getTargetMethod()){
			throw new IllegalArgumentException("target method must not be null");
		}
		if(null == targetProxyHandlers || targetProxyHandlers.length == 0){
			throw new IllegalArgumentException("target proxy handler must not be null");
		}
		this.targetClass = targetClass;
		this.targetInstance = targetInstance;
		this.targetMethod = targetProxyMethod.getTargetMethod();
		this.targetParameterDatas = targetParameterDatas;
		this.targetProxyMethod = targetProxyMethod;
		this.targetProxyHandlers = targetProxyHandlers;
	}

	@Override
	public Class<?> getTargetClass(){
		return this.targetClass;
	}

	@Override
	public Object getTargetInstance(){
		return this.targetInstance;
	}

	@Override
	public Method getTargetMethod(){
		return this.targetMethod;
	}

	@Override
	public Object[] getTargetParameterDatas(){
		return this.targetParameterDatas;
	}

	@Override
	public ProxyMethod getTargetProxyMethod(){
		return this.targetProxyMethod;
	}

	@Override
	public ProxyHandler[] getTargetProxyHandlers(){
		return this.targetProxyHandlers;
	}

	@Override
	public Object doProxyChain() throws Throwable{
		if(this.targetMethod.getDeclaringClass().equals(Object.class)){
			return this.targetProxyMethod.invokeTargetMethod(this.targetInstance,this.targetParameterDatas);
		}
		return this.doProxyChain(this.targetInstance,this.targetProxyMethod);
	}

	protected abstract Object doProxyChain(Object targetInstance,ProxyMethod proxyMethod) throws Throwable;

}