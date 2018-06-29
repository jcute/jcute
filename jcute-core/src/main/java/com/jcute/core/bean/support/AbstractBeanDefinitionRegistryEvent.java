package com.jcute.core.bean.support;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionRegistryEvent;

public abstract class AbstractBeanDefinitionRegistryEvent implements BeanDefinitionRegistryEvent{
	
	private BeanDefinitionFactory beanDefinitionFactory;
	private BeanDefinition beanDefinition;
	
	public AbstractBeanDefinitionRegistryEvent(BeanDefinitionFactory beanDefinitionFactory){
		this.beanDefinitionFactory = beanDefinitionFactory;
	}
	
	public void setBeanDefinition(BeanDefinition beanDefinition){
		this.beanDefinition = beanDefinition;
	}
	
	@Override
	public BeanDefinitionFactory getBeanDefinitionFactory(){
		return this.beanDefinitionFactory;
	}

	@Override
	public BeanDefinitionRegistry getBeanDefinitionRegistry(){
		return this.beanDefinitionFactory.getBeanDefinitionRegistry();
	}
	
	@Override
	public BeanDefinition getBeanDefinition(){
		return this.beanDefinition;
	}
	
}