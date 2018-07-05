package com.jcute.core.bean.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.util.StringUtils;

public class DefaultBeanDefinitionFactory extends AbstractBeanDefinitionFactory{
	
	public DefaultBeanDefinitionFactory(ApplicationContext applicationContext){
		super(applicationContext);
	}

	@Override
	public BeanDefinition createBeanDefinition(Class<?> beanType,String beanName,String beanScope){
		if(null == beanType){
			throw new IllegalArgumentException("bean type must not be null");
		}
		if(beanType.isInterface()){
			throw new IllegalArgumentException("bean type must not be interface");
		}
		if(Modifier.isAbstract(beanType.getModifiers())){
			throw new IllegalArgumentException("bean type must not be abstract");
		}
		if(StringUtils.isEmpty(beanName)){
			beanName = this.getBeanDefinitionResolver().resolveBeanName(beanType);
		}
		if(StringUtils.isEmpty(beanName)){
			beanName = beanType.getSimpleName();
			beanName = beanName.substring(0,1).toLowerCase() + beanName.substring(1);
		}
		if(StringUtils.isEmpty(beanScope)){
			beanScope = this.getBeanDefinitionResolver().resolveBeanScope(beanType);
		}
		if(StringUtils.isEmpty(beanScope)){
			beanScope = BeanDefinition.BEAN_SCOPE_SINGLETON;
		}
		return new DefaultBeanDefinitionForClass(this,beanType,beanName,beanScope);
	}

	@Override
	public BeanDefinition createBeanDefinition(Object beanInstance,String beanName){
		if(null == beanInstance){
			throw new IllegalArgumentException("bean instance must not be null");
		}
		Class<?> beanType = beanInstance.getClass();
		if(StringUtils.isEmpty(beanName)){
			beanName = this.getBeanDefinitionResolver().resolveBeanName(beanType);
		}
		if(StringUtils.isEmpty(beanName)){
			beanName = beanType.getSimpleName();
			beanName = beanName.substring(0,1).toLowerCase() + beanName.substring(1);
		}
		return new DefaultBeanDefinitionForObject(this,beanInstance,beanName);
	}
	
	@Override
	public BeanDefinition createBeanDefinition(BeanDefinition parentBeanDefinition,Method createBeanMethod,String beanName,String beanScope){
		if(null == parentBeanDefinition){
			throw new IllegalArgumentException("parent bean definition must not be null");
		}
		if(null == createBeanMethod){
			throw new IllegalArgumentException("create bean method must not be null");
		}
		if(StringUtils.isEmpty(beanName)){
			beanName = this.getBeanDefinitionResolver().resolveBeanName(createBeanMethod);
		}
		if(StringUtils.isEmpty(beanName)){
			beanName = createBeanMethod.getName();
		}
		if(StringUtils.isEmpty(beanScope)){
			beanScope = BeanDefinition.BEAN_SCOPE_SINGLETON;
		}
		return new DefaultBeanDefinitionForMethod(this,parentBeanDefinition,createBeanMethod,beanName,beanScope);
	}

}