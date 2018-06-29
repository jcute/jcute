package com.jcute.core.bean.support;

import java.util.LinkedHashSet;
import java.util.Set;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.exception.BeanInstanceCreateException;
import com.jcute.core.bean.exception.BeanInstanceDestoryException;
import com.jcute.core.bean.exception.BeanInstanceInitialException;
import com.jcute.core.bean.exception.BeanInstanceInjectException;
import com.jcute.core.bean.exception.BeanInstanceNotFoundException;
import com.jcute.core.bean.exception.BeanInstanceReleaseException;

public class DefaultBeanInstanceHandlerForPrototype extends AbstractBeanInstanceHandler{

	private Set<Object> beanInstances;
	
	public DefaultBeanInstanceHandlerForPrototype(BeanDefinition beanDefinition){
		super(beanDefinition);
	}
	
	@Override
	protected Object doGetBeanInstance() throws BeanInstanceNotFoundException{
		try{
			Object beanInstance = this.beanInstanceCreator.createBeanInstance();
			this.injectFields(beanInstance);
			this.injectMethods(beanInstance);
			this.invokeInitialMethods(beanInstance);
			this.beanInstances.add(beanInstance);
			return beanInstance;
		}catch(BeanInstanceCreateException e){
			throw new BeanInstanceNotFoundException();
		}catch(BeanInstanceInjectException e){
			throw new BeanInstanceNotFoundException();
		}
	}

	@Override
	public void onInitial() throws BeanInstanceInitialException{
		this.beanInstances = new LinkedHashSet<Object>();
	}

	@Override
	public void onDestory() throws BeanInstanceDestoryException{
		this.beanInstances.clear();
		this.beanInstances = null;
	}

	@Override
	public void onRelease() throws BeanInstanceReleaseException{
		if(null == this.beanInstances || this.beanInstances.size() == 0){
			return;
		}
		for(Object beanInstance : this.beanInstances){
			this.invokeDestoryMethods(beanInstance);
		}
	}

	@Override
	public void onCreate() throws BeanInstanceCreateException{
		
	}

	@Override
	public void onInject() throws BeanInstanceInjectException{
		
	}

}