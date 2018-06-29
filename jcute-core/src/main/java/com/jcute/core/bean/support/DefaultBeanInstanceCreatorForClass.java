package com.jcute.core.bean.support;

import java.lang.reflect.Constructor;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.bean.exception.BeanInstanceCreateException;
import com.jcute.core.util.ReflectionUtils;

public class DefaultBeanInstanceCreatorForClass extends AbstractBeanInstanceCreator{

	public DefaultBeanInstanceCreatorForClass(BeanDefinition beanDefinition){
		super(beanDefinition);
	}

	@Override
	protected Object doCreateBeanInstance() throws BeanInstanceCreateException{
		try{
			BeanDefinitionResolver beanDefinitionResolver = this.getBeanDefinition().getBeanDefinitionFactory().getBeanDefinitionResolver();
			Constructor<?> constructor = beanDefinitionResolver.resolveBeanConstructor(this.getBeanDefinition().getBeanType());
			if(null == constructor){
				throw new BeanInstanceCreateException(String.format("%s missing constructor",this.getBeanDefinition().toString()));
			}
			Object[] arguments = beanDefinitionResolver.getArguments(constructor);
			return ReflectionUtils.invokeConstructor(constructor,arguments);
		}catch(Exception e){
			throw new BeanInstanceCreateException(e.getMessage(),e);
		}
	}

}