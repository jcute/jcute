package com.jcute.core.bean;

import java.lang.reflect.Method;
import java.util.Map;

import com.jcute.core.bean.exception.BeanDefinitionMultipleException;
import com.jcute.core.bean.exception.BeanDefinitionNotFoundException;
import com.jcute.core.config.ConfigSourceManager;
import com.jcute.core.toolkit.cycle.Stable;
import com.jcute.core.toolkit.proxy.ProxyManager;

public interface BeanDefinitionFactory extends Stable<BeanDefinitionFactoryEvent,BeanDefinitionFactoryListener>{
	
	public BeanDefinitionRegistry getBeanDefinitionRegistry();
	
	public BeanDefinitionResolver getBeanDefinitionResolver();
	
	public ConfigSourceManager getConfigSourceManager();
	
	public ProxyManager getProxyManager();
	
	public BeanDefinition getBeanDefinition(Class<?> beanType,String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	public BeanDefinition getBeanDefinition(Class<?> beanType) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	public BeanDefinition getBeanDefinition(String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException;

	public Map<String,BeanDefinition> getBeanDefinitions(String beanName) throws BeanDefinitionNotFoundException;

	public Map<String,BeanDefinition> getBeanDefinitions(Class<?> beanType) throws BeanDefinitionNotFoundException;

	public Map<String,BeanDefinition> getAllBeanDefinitions() throws BeanDefinitionNotFoundException;
	
	public BeanDefinition createBeanDefinition(Class<?> beanType,String beanName,String beanScope);
	public BeanDefinition createBeanDefinition(Class<?> beanType,String beanName);
	public BeanDefinition createBeanDefinition(Class<?> beanType);
	
	public BeanDefinition createBeanDefinition(Object beanInstance);
	public BeanDefinition createBeanDefinition(Object beanInstance,String beanName);
	
	public BeanDefinition createBeanDefinition(BeanDefinition parentBeanDefinition,Method createBeanMethod,String beanName,String beanScope);
	public BeanDefinition createBeanDefinition(BeanDefinition parentBeanDefinition,Method createBeanMethod,String beanName);
	public BeanDefinition createBeanDefinition(BeanDefinition parentBeanDefinition,Method createBeanMethod);
	
}