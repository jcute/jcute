package com.jcute.core.toolkit.proxy.support;

import com.jcute.core.toolkit.proxy.ProxyHandler;
import com.jcute.core.toolkit.proxy.ProxyMethod;

public class ProxyChainForInterface extends AbstractProxyChain{

	public ProxyChainForInterface(Class<?> targetClass,Object targetInstance,Object[] targetParameterDatas,ProxyMethod targetProxyMethod,ProxyHandler[] targetProxyHandlers){
		super(targetClass,targetInstance,targetParameterDatas,targetProxyMethod,targetProxyHandlers);
	}

	@Override
	protected Object doProxyChain(Object targetInstance,ProxyMethod proxyMethod) throws Throwable{
		Object result = null;
		for(int i=0;i<this.targetProxyHandlers.length;i++){
			result = this.targetProxyHandlers[i].getFactory().execute(this);
		}
		return result;
	}

}