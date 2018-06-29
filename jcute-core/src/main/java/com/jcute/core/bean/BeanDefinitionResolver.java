package com.jcute.core.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import com.jcute.core.bean.exception.BeanDefinitionMultipleException;
import com.jcute.core.bean.exception.BeanDefinitionNotFoundException;
import com.jcute.core.bean.exception.BeanInstanceInjectException;

public interface BeanDefinitionResolver{
	
	public Object[] getArguments(Method method);
	
	public Object[] getArguments(Constructor<?> constructor);
	
	public Method[] getInitialMethod(Class<?> beanType);
	
	public Method[] getDestoryMethod(Class<?> beanType);
	
	public String resolveBeanName(Class<?> beanType);
	
	public String resolveBeanName(Method method);
	
	public String resolveBeanScope(Class<?> beanType);
	
	public String resolveBeanScope(Method method);
	
	public Constructor<?> resolveBeanConstructor(Class<?> beanType);
	
	public void injectField(Object beanInstance)throws BeanInstanceInjectException;
	
	public void inejctMethod(Object beanInstance)throws BeanInstanceInjectException;
	
	public BeanDefinitionFactory getBeanDefinitionFactory();
	
	public BeanDefinitionRegistry getBeanDefinitionRegistry();
	
	public Set<BeanDefinition> getSortBeanDefinitions(Map<String,BeanDefinition> beanDefinitions)throws BeanDefinitionNotFoundException, BeanDefinitionMultipleException;
	
	public Set<BeanDefinition> getBeanDefinitionProxy(Class<?> beanType);
	
}