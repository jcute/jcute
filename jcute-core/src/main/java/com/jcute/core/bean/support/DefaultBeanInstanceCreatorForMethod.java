package com.jcute.core.bean.support;

import java.lang.reflect.Method;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.bean.exception.BeanInstanceCreateException;
import com.jcute.core.util.ReflectionUtils;

public class DefaultBeanInstanceCreatorForMethod extends AbstractBeanInstanceCreator{

	private BeanDefinition parentBeanDefinition;
	private Method createBeanMethod;
	
	public DefaultBeanInstanceCreatorForMethod(BeanDefinition beanDefinition,BeanDefinition parentBeanDefinition,Method createBeanMethod){
		super(beanDefinition);
		if(null == parentBeanDefinition){
			throw new IllegalArgumentException("parent bean definition must not be null");
		}
		if(null == createBeanMethod){
			throw new IllegalArgumentException("create bean method must not be null");
		}
		this.parentBeanDefinition = parentBeanDefinition;
		this.createBeanMethod = createBeanMethod;
	}
	
	@Override
	protected Object doCreateBeanInstance() throws BeanInstanceCreateException{
		try{
			BeanDefinitionResolver beanDefinitionResolver = this.getBeanDefinition().getBeanDefinitionFactory().getBeanDefinitionResolver();
			Object[] arguments = beanDefinitionResolver.getArguments(this.createBeanMethod);
			Object beanInstance = ReflectionUtils.invokeMethod(this.createBeanMethod,this.parentBeanDefinition.getBeanInstance(),arguments);
			if(null == beanInstance){
				throw new BeanInstanceCreateException(String.format("%s.%s return value is null",this.beanDefinition.toString(),this.createBeanMethod.getName()));
			}
			return beanInstance;
		}catch(Exception e){
			throw new BeanInstanceCreateException(this.beanDefinition.toString());
		}
	}
	
}