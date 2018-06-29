package com.jcute.core.interceptor;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.toolkit.proxy.ProxyFactory;
import com.jcute.core.toolkit.proxy.ProxyHandler;

public class InterceptorHandler implements ProxyHandler{

	private BeanDefinition beanDefinition;

	public InterceptorHandler(BeanDefinition beanDefinition){
		if(null == beanDefinition){
			throw new IllegalArgumentException("bean definition must not be null");
		}
		this.beanDefinition = beanDefinition;
	}

	@Override
	public ProxyFactory getFactory(){
		Object beanInstance = this.beanDefinition.getBeanInstance();
		if(beanInstance instanceof ProxyFactory){
			return (ProxyFactory)beanInstance;
		}
		throw new IllegalStateException(String.format("%s must implement ProxyFactory",this.beanDefinition.toString()));
	}

}