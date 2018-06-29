package com.jcute.core.plugin;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.toolkit.cycle.StableEvent;

public interface PluginManagerEvent extends StableEvent{
	
	public PluginManager getPluginManager();
	
	public ApplicationContext getApplicationContext();
	
	public BeanDefinitionFactory getBeanDefinitionFactory();
	
	public BeanDefinitionRegistry getBeanDefinitionRegistry();
	
	public BeanDefinitionResolver getBeanDefinitionResolver();
	
	public Plugin getPlugin();
	
}