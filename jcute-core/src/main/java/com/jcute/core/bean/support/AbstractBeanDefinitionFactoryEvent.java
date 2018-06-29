package com.jcute.core.bean.support;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionFactoryEvent;
import com.jcute.core.bean.BeanDefinitionRegistry;

public abstract class AbstractBeanDefinitionFactoryEvent implements BeanDefinitionFactoryEvent{

	private BeanDefinitionFactory beanDefinitionFactory;

	public AbstractBeanDefinitionFactoryEvent(BeanDefinitionFactory beanDefinitionFactory){
		this.beanDefinitionFactory = beanDefinitionFactory;
	}

	@Override
	public BeanDefinitionFactory getBeanDefinitionFactory(){
		return this.beanDefinitionFactory;
	}

	@Override
	public BeanDefinitionRegistry getBeanDefinitionRegistry(){
		return this.beanDefinitionFactory.getBeanDefinitionRegistry();
	}

}