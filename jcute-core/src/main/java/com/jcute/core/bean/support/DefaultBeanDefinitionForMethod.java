package com.jcute.core.bean.support;

import java.lang.reflect.Method;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionType;
import com.jcute.core.bean.BeanInstanceCreator;
import com.jcute.core.bean.BeanInstanceHandler;
import com.jcute.core.util.StringUtils;

public class DefaultBeanDefinitionForMethod extends AbstractBeanDefinition{
	
	protected BeanInstanceHandler beanInstanceHandler;
	protected BeanInstanceCreator beanInstanceCreator;
	protected BeanDefinition parentBeanDefinition;
	protected Method createBeanMethod;
	
	public DefaultBeanDefinitionForMethod(BeanDefinitionFactory beanDefinitionFactory,BeanDefinition parentBeanDefinition,Method createBeanMethod,String beanName,String beanScope){
		super(beanDefinitionFactory,createBeanMethod.getReturnType(),StringUtils.isEmpty(beanName) ? createBeanMethod.getName() : beanName,StringUtils.isEmpty(beanScope) ? BeanDefinition.BEAN_SCOPE_SINGLETON : beanScope);
		if(null == parentBeanDefinition){
			throw new IllegalArgumentException("parent bean definition must not be null");
		}
		this.parentBeanDefinition = parentBeanDefinition;
		this.createBeanMethod = createBeanMethod;
		this.beanInstanceCreator = new DefaultBeanInstanceCreatorForMethod(this,this.parentBeanDefinition,this.createBeanMethod);
		if(this.isSingleton()){
			this.beanInstanceHandler = new DefaultBeanInstanceHandlerForSingleton(this);
		}else if(this.isPrototype()){
			this.beanInstanceHandler = new DefaultBeanInstanceHandlerForPrototype(this);
		}else{
			throw new UnsupportedOperationException("bean scope not support");
		}
	}
	
	public final BeanDefinition getParentBeanDefinition(){
		return this.parentBeanDefinition;
	}
	
	public final Method getCreateBeanMethod(){
		return this.createBeanMethod;
	}
	
	@Override
	public BeanDefinitionType getBeanDefinitionType(){
		return BeanDefinitionType.FromMethod;
	}
	
	@Override
	public BeanInstanceHandler getBeanInstanceHandler(){
		return this.beanInstanceHandler;
	}

	@Override
	public BeanInstanceCreator getBeanInstanceCreator(){
		return this.beanInstanceCreator;
	}
	
}