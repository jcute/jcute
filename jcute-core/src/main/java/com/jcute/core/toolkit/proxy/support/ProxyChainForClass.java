package com.jcute.core.toolkit.proxy.support;

import com.jcute.core.toolkit.proxy.ProxyFactory;
import com.jcute.core.toolkit.proxy.ProxyHandler;
import com.jcute.core.toolkit.proxy.ProxyMethod;

public class ProxyChainForClass extends AbstractProxyChain{
	
	private volatile int invokeIndex;
	
	public ProxyChainForClass(Class<?> targetClass,Object targetInstance,Object[] targetParameterDatas,ProxyMethod targetProxyMethod,ProxyHandler[] targetProxyHandlers){
		super(targetClass,targetInstance,targetParameterDatas,targetProxyMethod,targetProxyHandlers);
	}
	
	@Override
	protected Object doProxyChain(Object targetInstance,ProxyMethod proxyMethod) throws Throwable{
		Object result = null;
		if(this.invokeIndex < this.targetProxyHandlers.length){
			ProxyHandler proxyHandler = this.targetProxyHandlers[this.invokeIndex++];
			if(null == proxyHandler){
				throw new IllegalStateException("proxy handler is null");
			}
			ProxyFactory proxyFactory = proxyHandler.getFactory();
			if(null == proxyFactory){
				throw new IllegalStateException("proxy factory is null");
			}
			result = proxyFactory.execute(this);
		}else{
			result = this.targetProxyMethod.invokeTargetMethod(this.targetInstance,this.targetParameterDatas);
		}
		return result;
	}
	
}