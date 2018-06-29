package com.jcute.core.bean;

import com.jcute.core.toolkit.cycle.StableEvent;

public interface BeanDefinitionRegistryEvent extends StableEvent{

	public BeanDefinitionFactory getBeanDefinitionFactory();

	public BeanDefinitionRegistry getBeanDefinitionRegistry();

	public BeanDefinition getBeanDefinition();

	public void setBeanDefinition(BeanDefinition beanDefinition);

}