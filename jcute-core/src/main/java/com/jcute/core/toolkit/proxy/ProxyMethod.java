package com.jcute.core.toolkit.proxy;

import java.lang.reflect.Method;

public interface ProxyMethod{

	public Method getTargetMethod();

	public Object invokeTargetMethod(Object targetInstance,Object[] targetParameterDatas) throws Throwable;

	public Object invokeTargetMethod(Object targetInstance) throws Throwable;

}