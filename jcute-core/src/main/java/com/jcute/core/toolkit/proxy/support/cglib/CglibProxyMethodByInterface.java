package com.jcute.core.toolkit.proxy.support.cglib;

import java.lang.reflect.Method;

import javax.naming.OperationNotSupportedException;

import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyMethodByInterface extends AbstractCglibProxyMethod{

	public CglibProxyMethodByInterface(Method targetMethod,MethodProxy methodProxy){
		super(targetMethod,methodProxy);
	}

	@Override
	public Object invokeTargetMethod(Object targetInstance,Object[] targetParameterDatas) throws Throwable{
		throw new OperationNotSupportedException("must override ProxyFactoryForInterface.execute method");
	}
	
}