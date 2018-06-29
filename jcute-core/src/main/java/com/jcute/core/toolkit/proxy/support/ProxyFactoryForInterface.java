package com.jcute.core.toolkit.proxy.support;

import java.lang.reflect.Method;

import com.jcute.core.toolkit.proxy.ProxyChain;
import com.jcute.core.toolkit.proxy.ProxyFactory;

public abstract class ProxyFactoryForInterface implements ProxyFactory{
	
	@Override
	public final Object execute(ProxyChain proxyChain) throws Throwable{
		return this.execute(proxyChain.getTargetInstance(),proxyChain.getTargetMethod(),proxyChain.getTargetParameterDatas());
	}
	
	protected abstract Object execute(Object targetInstance,Method targetMethod,Object[] targetParameterDatas);
	
}