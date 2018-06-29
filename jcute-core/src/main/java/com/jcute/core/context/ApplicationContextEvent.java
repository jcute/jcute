package com.jcute.core.context;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.toolkit.cycle.StableEvent;

public interface ApplicationContextEvent extends StableEvent{
	
	public ApplicationContext getApplicationContext();
	
	public BeanDefinitionFactory getBeanDefinitionFactory();
	
	public BeanDefinitionRegistry getBeanDefinitionRegistry();
	
	public BeanDefinitionResolver getBeanDefinitionResolver();
	
}