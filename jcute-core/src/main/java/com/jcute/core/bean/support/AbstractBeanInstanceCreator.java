package com.jcute.core.bean.support;

import java.util.Set;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.bean.BeanInstanceCreator;
import com.jcute.core.bean.BeanInstanceHandler;
import com.jcute.core.bean.exception.BeanInstanceCreateException;
import com.jcute.core.interceptor.InterceptorHandler;
import com.jcute.core.toolkit.proxy.ProxyManager;

public abstract class AbstractBeanInstanceCreator implements BeanInstanceCreator{

	protected BeanDefinition beanDefinition;
	protected BeanInstanceHandler beanInstanceHandler;

	public AbstractBeanInstanceCreator(BeanDefinition beanDefinition){
		if(null == beanDefinition){
			throw new IllegalArgumentException("bean definition must not be null");
		}
		this.beanDefinition = beanDefinition;
		this.beanInstanceHandler = beanDefinition.getBeanInstanceHandler();
	}

	@Override
	public Object createBeanInstance() throws BeanInstanceCreateException{
		Object beanInstance = this.doCreateBeanInstance();
		if(null == beanInstance){
			throw new BeanInstanceCreateException(this.beanDefinition.toString());
		}
		return this.createBeanProxy(beanInstance);
	}

	@Override
	public BeanDefinition getBeanDefinition(){
		return this.beanDefinition;
	}

	@Override
	public BeanInstanceHandler getBeanInstanceHandler(){
		return this.beanInstanceHandler;
	}
	
	protected Object createBeanProxy(Object beanInstance){
		ProxyManager proxyManager = this.beanDefinition.getBeanDefinitionFactory().getProxyManager();
		BeanDefinitionResolver beanDefinitionResolver = this.beanDefinition.getBeanDefinitionFactory().getBeanDefinitionResolver();
		Set<BeanDefinition> beanDefinitions = beanDefinitionResolver.getBeanDefinitionProxy(beanInstance.getClass());
		if(null == beanDefinitions || beanDefinitions.size() == 0){
			return beanInstance;
		}
		InterceptorHandler[] interceptorHandlers = new InterceptorHandler[beanDefinitions.size()];
		int index = 0;
		for(BeanDefinition beanDefinition : beanDefinitions){
			interceptorHandlers[index++] = new InterceptorHandler(beanDefinition);
		}
		return proxyManager.createProxyByInstance(beanInstance,interceptorHandlers);
	}
	
	protected abstract Object doCreateBeanInstance() throws BeanInstanceCreateException;

}