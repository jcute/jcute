package com.jcute.core.toolkit.proxy.support.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyMethodByClass extends AbstractCglibProxyMethod{

	public CglibProxyMethodByClass(Method targetMethod,MethodProxy methodProxy){
		super(targetMethod,methodProxy);
	}

	@Override
	public Object invokeTargetMethod(Object targetInstance,Object[] targetParameterDatas) throws Throwable{
		return this.targetMethodProxy.invokeSuper(targetInstance,targetParameterDatas);
	}
	
}