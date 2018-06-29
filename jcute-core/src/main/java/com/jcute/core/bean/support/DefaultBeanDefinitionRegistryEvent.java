package com.jcute.core.bean.support;

import com.jcute.core.bean.BeanDefinitionFactory;

public class DefaultBeanDefinitionRegistryEvent extends AbstractBeanDefinitionRegistryEvent{

	public DefaultBeanDefinitionRegistryEvent(BeanDefinitionFactory beanDefinitionFactory){
		super(beanDefinitionFactory);
	}

}