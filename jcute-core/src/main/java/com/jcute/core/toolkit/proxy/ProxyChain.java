package com.jcute.core.toolkit.proxy;

import java.lang.reflect.Method;

public interface ProxyChain{

	public Class<?> getTargetClass();

	public Object getTargetInstance();

	public Method getTargetMethod();

	public Object[] getTargetParameterDatas();

	public ProxyMethod getTargetProxyMethod();

	public ProxyHandler[] getTargetProxyHandlers();

	public Object doProxyChain() throws Throwable;

}