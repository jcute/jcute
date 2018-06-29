package com.jcute.core.bean.support;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.exception.BeanInstanceCreateException;
import com.jcute.core.bean.exception.BeanInstanceDestoryException;
import com.jcute.core.bean.exception.BeanInstanceInitialException;
import com.jcute.core.bean.exception.BeanInstanceInjectException;
import com.jcute.core.bean.exception.BeanInstanceNotFoundException;
import com.jcute.core.bean.exception.BeanInstanceReleaseException;

public class DefaultBeanInstanceHandlerForSingleton extends AbstractBeanInstanceHandler{

	private Object beanInstance;

	public DefaultBeanInstanceHandlerForSingleton(BeanDefinition beanDefinition){
		super(beanDefinition);
	}
	
	@Override
	protected Object doGetBeanInstance() throws BeanInstanceNotFoundException{
		return this.beanInstance;
	}

	@Override
	public void onInitial() throws BeanInstanceInitialException{
		this.invokeInitialMethods(this.beanInstance);
	}

	@Override
	public void onDestory() throws BeanInstanceDestoryException{
		this.beanInstance = null;
	}

	@Override
	public void onRelease() throws BeanInstanceReleaseException{
		this.invokeDestoryMethods(this.beanInstance);
	}

	@Override
	public void onCreate() throws BeanInstanceCreateException{
		this.beanInstance = this.beanInstanceCreator.createBeanInstance();
	}

	@Override
	public void onInject() throws BeanInstanceInjectException{
		this.injectFields(this.beanInstance);
		this.injectMethods(this.beanInstance);
	}
	
}