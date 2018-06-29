package com.jcute.core.bean.support;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionType;
import com.jcute.core.bean.BeanInstanceCreator;
import com.jcute.core.bean.BeanInstanceHandler;

public class DefaultBeanDefinitionForObject extends AbstractBeanDefinition{

	protected BeanInstanceHandler beanInstanceHandler;
	protected BeanInstanceCreator beanInstanceCreator;
	protected Object beanInstance;
	
	public DefaultBeanDefinitionForObject(BeanDefinitionFactory beanDefinitionFactory,Object beanInstance,String beanName){
		super(beanDefinitionFactory,beanInstance.getClass(),beanName,null);
		this.beanInstance = beanInstance;
		this.beanInstanceCreator = new DefaultBeanInstanceCreatorForObject(this,this.beanInstance);
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
		return BeanDefinitionType.FromObject;
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