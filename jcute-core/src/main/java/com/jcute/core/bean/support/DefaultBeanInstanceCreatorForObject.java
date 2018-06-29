package com.jcute.core.bean.support;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.exception.BeanInstanceCreateException;

public class DefaultBeanInstanceCreatorForObject extends AbstractBeanInstanceCreator{

	private Object beanInstance;
	
	public DefaultBeanInstanceCreatorForObject(BeanDefinition beanDefinition,Object beanInstance){
		super(beanDefinition);
		if(null == beanInstance){
			throw new IllegalArgumentException("bean instance must not be null");
		}
		this.beanInstance = beanInstance;
	}
	
	@Override
	protected Object doCreateBeanInstance() throws BeanInstanceCreateException{
		return this.beanInstance;
	}
	
}