package com.jcute.core.bean;

import com.jcute.core.toolkit.cycle.StableEvent;

public interface BeanDefinitionFactoryEvent extends StableEvent{
	
	public BeanDefinitionFactory getBeanDefinitionFactory();
	
	public BeanDefinitionRegistry getBeanDefinitionRegistry();
	
}