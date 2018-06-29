package com.jcute.core.bean.support;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionType;
import com.jcute.core.bean.BeanInstanceCreator;
import com.jcute.core.bean.BeanInstanceHandler;

public class DefaultBeanDefinitionForClass extends AbstractBeanDefinition{
	
	protected BeanInstanceHandler beanInstanceHandler;
	protected BeanInstanceCreator beanInstanceCreator;
	
	public DefaultBeanDefinitionForClass(BeanDefinitionFactory beanDefinitionFactory,Class<?> beanType,String beanName,String beanScope){
		super(beanDefinitionFactory,beanType,beanName,beanScope);
		this.beanInstanceCreator = new DefaultBeanInstanceCreatorForClass(this);
		if(this.isSingleton()){
			this.beanInstanceHandler = new DefaultBeanInstanceHandlerForSingleton(this);
		}else if(this.isPrototype()){
			this.beanInstanceHandler = new DefaultBeanInstanceHandlerForPrototype(this);
		}else{
			throw new UnsupportedOperationException("bean scope not support");
		}
	}
	
	@Override
	public BeanDefinitionType getBeanDefinitionType(){
		return BeanDefinitionType.FromClass;
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