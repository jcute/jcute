package com.jcute.core.toolkit.proxy.support.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import com.jcute.core.toolkit.proxy.support.AbstractProxyMethod;

public abstract class AbstractCglibProxyMethod extends AbstractProxyMethod{

	protected MethodProxy targetMethodProxy;

	public AbstractCglibProxyMethod(Method targetMethod,MethodProxy methodProxy){
		super(targetMethod);
		if(null == methodProxy){
			throw new IllegalArgumentException("cglib method proxy must not be null");
		}
		this.targetMethodProxy = methodProxy;
	}
	
	public MethodProxy getTargetMethodProxy(){
		return targetMethodProxy;
	}

}