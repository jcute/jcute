package com.jcute.core.bean;

import com.jcute.core.bean.exception.BeanInstanceNotFoundException;

public interface BeanInstanceHandler extends BeanInstanceCycle{

	public BeanDefinition getBeanDefinition();
	
	public BeanInstanceCreator getBeanInstanceCreator();
	
	public Object getBeanInstance() throws BeanInstanceNotFoundException;

}