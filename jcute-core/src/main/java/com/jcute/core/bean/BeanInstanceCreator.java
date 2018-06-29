package com.jcute.core.bean;

import com.jcute.core.bean.exception.BeanInstanceCreateException;

public interface BeanInstanceCreator{

	public Object createBeanInstance() throws BeanInstanceCreateException;
	
	public BeanDefinition getBeanDefinition();
	
	public BeanInstanceHandler getBeanInstanceHandler();

}