package com.jcute.core.bean.support;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionType;
import com.jcute.core.bean.exception.BeanInstanceNotFoundException;
import com.jcute.core.util.StringUtils;

public abstract class AbstractBeanDefinition implements BeanDefinition{
	
	protected BeanDefinitionFactory beanDefinitionFactory;

	protected Class<?> beanType;
	protected String beanName;
	protected String beanScope;

	public AbstractBeanDefinition(BeanDefinitionFactory beanDefinitionFactory,Class<?> beanType,String beanName,String beanScope){
		if(null == beanDefinitionFactory){
			throw new IllegalArgumentException("bean definition factory must not be null");
		}
		if(null == beanType){
			throw new IllegalArgumentException("bean class must not be null");
		}
		if(StringUtils.isEmpty(beanName)){
			beanName = beanType.getSimpleName();
			beanName = beanName.substring(0,1).toLowerCase() + beanName.substring(1);
		}
		if(StringUtils.isEmpty(beanScope)){
			beanScope = BEAN_SCOPE_SINGLETON;
		}
		this.beanType = beanType;
		this.beanName = beanName;
		this.beanScope = beanScope.toLowerCase();
		this.beanDefinitionFactory = beanDefinitionFactory;
	}

	@Override
	public Class<?> getBeanType(){
		return this.beanType;
	}

	@Override
	public String getBeanName(){
		return this.beanName;
	}

	@Override
	public String getBeanScope(){
		return this.beanScope;
	}

	@Override
	public boolean isPrototype(){
		return BEAN_SCOPE_PROTOTYPE.equals(this.beanScope);
	}

	@Override
	public boolean isSingleton(){
		return BEAN_SCOPE_SINGLETON.equals(this.beanScope);
	}

	@Override
	public boolean isAssignable(Class<?> beanType){
		if(null == beanType){
			return false;
		}
		return beanType.isAssignableFrom(this.beanType);
	}

	@Override
	public boolean isDefinitionFromClass(){
		return this.getBeanDefinitionType() == BeanDefinitionType.FromClass;
	}

	@Override
	public boolean isDefinitionFromMethod(){
		return this.getBeanDefinitionType() == BeanDefinitionType.FromMethod;
	}

	@Override
	public boolean isDefinitionFromObject(){
		return this.getBeanDefinitionType() == BeanDefinitionType.FromObject;
	}

	@Override
	public Object getBeanInstance(){
		try{
			return this.getBeanInstanceHandler().getBeanInstance();
		}catch(BeanInstanceNotFoundException e){
			return null;
		}
	}

	@Override
	public BeanDefinitionFactory getBeanDefinitionFactory(){
		return this.beanDefinitionFactory;
	}

	@Override
	public int hashCode(){
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if(null == obj){
			return false;
		}
		if(obj instanceof BeanDefinition){
			return this.toString().equals(obj.toString());
		}
		return false;
	}

	@Override
	public String toString(){
		return String.format("[%s-%s]%s#%s",this.getBeanScope(),this.getBeanDefinitionType().toString().toLowerCase(),this.getBeanType().getName(),this.getBeanName());
	}
	
}