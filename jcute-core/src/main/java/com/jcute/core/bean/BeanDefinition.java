package com.jcute.core.bean;

public interface BeanDefinition{
	
	public static final String BEAN_SCOPE_SINGLETON = "singleton";
	public static final String BEAN_SCOPE_PROTOTYPE = "prototype";
	
	public Class<?> getBeanType();

	public String getBeanName();

	public String getBeanScope();

	public BeanDefinitionType getBeanDefinitionType();

	public boolean isPrototype();

	public boolean isSingleton();

	public boolean isAssignable(Class<?> beanType);

	public boolean isDefinitionFromClass();

	public boolean isDefinitionFromMethod();

	public boolean isDefinitionFromObject();

	public Object getBeanInstance();

	public BeanDefinitionFactory getBeanDefinitionFactory();

	public BeanInstanceHandler getBeanInstanceHandler();
	
	public BeanInstanceCreator getBeanInstanceCreator();

}