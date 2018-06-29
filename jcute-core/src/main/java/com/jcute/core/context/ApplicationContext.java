package com.jcute.core.context;

import java.util.Map;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.config.ConfigSourceManager;
import com.jcute.core.toolkit.cycle.Stable;

public interface ApplicationContext extends Stable<ApplicationContextEvent,ApplicationContextListener>{
	
	public BeanDefinitionFactory getBeanDefinitionFactory();
	
	public BeanDefinitionRegistry getBeanDefinitionRegistry();
	
	public BeanDefinitionResolver getBeanDefinitionResolver();
	
	public ConfigSourceManager getConfigSourceManager();
	
	public <T> T getBean(Class<?> beanType,String beanName);
	
	public <T> T getBean(Class<?> beanType);
	
	public <T> T getBean(String beanName);
	
	public <T> Map<String,T> getBeans(Class<?> beanType);
	
}