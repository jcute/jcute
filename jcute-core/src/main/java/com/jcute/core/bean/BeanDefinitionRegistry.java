package com.jcute.core.bean;

import java.util.Map;
import java.util.Map.Entry;

import com.jcute.core.bean.exception.BeanDefinitionExistsException;
import com.jcute.core.toolkit.cycle.Stable;

public interface BeanDefinitionRegistry extends Iterable<Entry<String,BeanDefinition>>,Stable<BeanDefinitionRegistryEvent,BeanDefinitionRegistryListener>{
	
	public BeanDefinitionFactory getBeanDefinitionFactory();
	
	public Map<String,BeanDefinition> getAllBeanDefinitions();
	
	public void attachBeanDefinition(BeanDefinition beanDefinition)throws BeanDefinitionExistsException;
	
	public boolean containsBeanDefinition(BeanDefinition beanDefinition);
	
	public boolean containsBeanDefinition(String beanName);
	
	public void attachBeanDefinitionAddSuccessListener(BeanDefinitionRegistryListener listener);
	public void detachBeanDefinitionAddSuccessListener(BeanDefinitionRegistryListener listener);
	
	public void attachBeanDefinitionDelSuccessListener(BeanDefinitionRegistryListener listener);
	public void detachBeanDefinitionDelSuccessListener(BeanDefinitionRegistryListener listener);
	
}