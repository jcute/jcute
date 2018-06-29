package com.jcute.core.bean.support;

import java.lang.reflect.Method;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.bean.BeanInstanceCreator;
import com.jcute.core.bean.BeanInstanceHandler;
import com.jcute.core.bean.exception.BeanInstanceInjectException;
import com.jcute.core.bean.exception.BeanInstanceNotFoundException;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.util.ReflectionUtils;

public abstract class AbstractBeanInstanceHandler implements BeanInstanceHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractBeanInstanceHandler.class);
	
	protected BeanDefinition beanDefinition;
	protected BeanInstanceCreator beanInstanceCreator;
	protected BeanDefinitionResolver beanDefinitionResolver;
	
	public AbstractBeanInstanceHandler(BeanDefinition beanDefinition){
		if(null == beanDefinition){
			throw new IllegalArgumentException("bean definition must not be null");
		}
		this.beanDefinition = beanDefinition;
		this.beanInstanceCreator = beanDefinition.getBeanInstanceCreator();
		this.beanDefinitionResolver = this.beanDefinition.getBeanDefinitionFactory().getBeanDefinitionResolver();
	}
	
	@Override
	public BeanDefinition getBeanDefinition(){
		return this.beanDefinition;
	}
	
	@Override
	public BeanInstanceCreator getBeanInstanceCreator(){
		return this.beanInstanceCreator;
	}
	
	@Override
	public Object getBeanInstance() throws BeanInstanceNotFoundException{
		Object beanInstance = this.doGetBeanInstance();
		if(null == beanInstance){
			throw new BeanInstanceNotFoundException(this.beanDefinition.toString());
		}
		return beanInstance;
	}
	
	protected void invokeInitialMethods(Object beanInstance){
		Method[] methods = this.beanDefinitionResolver.getInitialMethod(beanInstance.getClass());
		if(null == methods || methods.length == 0){
			return;
		}
		for(int i=0;i<methods.length;i++){
			try{
				Method method = methods[i];
				Object[] arguments = this.beanDefinitionResolver.getArguments(method);
				ReflectionUtils.invokeMethod(method,beanInstance,arguments);
			}catch(Exception e){
				e.printStackTrace();
				logger.warn("invoke initial method failed {}",e.getMessage(),e);
			}
		}
	}
	
	protected void invokeDestoryMethods(Object beanInstance){
		Method[] methods = this.beanDefinitionResolver.getDestoryMethod(beanInstance.getClass());
		if(null == methods || methods.length == 0){
			return;
		}
		for(int i=0;i<methods.length;i++){
			try{
				Method method = methods[i];
				Object[] arguments = this.beanDefinitionResolver.getArguments(method);
				ReflectionUtils.invokeMethod(method,beanInstance,arguments);
			}catch(Exception e){
				logger.warn("invoke destory method failed {}",e.getMessage(),e);
			}
		}
	}
	
	protected void injectFields(Object beanInstance) throws BeanInstanceInjectException{
		this.beanDefinitionResolver.injectField(beanInstance);
	}
	
	protected void injectMethods(Object beanInstance) throws BeanInstanceInjectException{
		this.beanDefinitionResolver.inejctMethod(beanInstance);
	}
	
	protected abstract Object doGetBeanInstance()throws BeanInstanceNotFoundException;
	
}