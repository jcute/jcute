package com.jcute.core.bean.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionFactoryEvent;
import com.jcute.core.bean.BeanDefinitionFactoryListener;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.bean.exception.BeanDefinitionMultipleException;
import com.jcute.core.bean.exception.BeanDefinitionNotFoundException;
import com.jcute.core.config.ConfigSourceManager;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.proxy.ProxyManager;
import com.jcute.core.util.StringUtils;

public abstract class AbstractBeanDefinitionFactory extends AbstractStable<BeanDefinitionFactoryEvent,BeanDefinitionFactoryListener> implements BeanDefinitionFactory{

	private ApplicationContext applicationContext;

	public AbstractBeanDefinitionFactory(ApplicationContext applicationContext){
		if(null == applicationContext){
			throw new IllegalArgumentException("application context must not be null");
		}
		this.applicationContext = applicationContext;
	}

	@Override
	protected void doStart() throws Exception{
		this.getBeanDefinitionRegistry().attachBeanDefinition(this.createBeanDefinition(this));
		this.getBeanDefinitionRegistry().attachBeanDefinition(this.createBeanDefinition(this.applicationContext));
		this.getBeanDefinitionRegistry().attachBeanDefinition(this.createBeanDefinition(this.getBeanDefinitionRegistry()));
		this.getBeanDefinitionRegistry().attachBeanDefinition(this.createBeanDefinition(this.getBeanDefinitionResolver()));
	}

	@Override
	protected void doClose() throws Exception{
	}

	@Override
	public ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}

	@Override
	public BeanDefinitionRegistry getBeanDefinitionRegistry(){
		return this.applicationContext.getBeanDefinitionRegistry();
	}

	@Override
	public BeanDefinitionResolver getBeanDefinitionResolver(){
		return this.applicationContext.getBeanDefinitionResolver();
	}

	@Override
	public ConfigSourceManager getConfigSourceManager(){
		return this.applicationContext.getConfigSourceManager();
	}

	@Override
	public ProxyManager getProxyManager(){
		return this.applicationContext.getProxyManager();
	}

	@Override
	public BeanDefinition getBeanDefinition(Class<?> beanType,String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		if(null == beanType && StringUtils.isEmpty(beanName)){
			throw new IllegalArgumentException("bean type and bean name at least one is not empty");
		}
		if(null != beanType && StringUtils.isEmpty(beanName)){
			return this.getBeanDefinition(beanType);
		}else if(null == beanType && (!StringUtils.isEmpty(beanName))){
			return this.getBeanDefinition(beanName);
		}else{
			Map<String,BeanDefinition> definitions = this.getBeanDefinitions(beanType);
			if(null == definitions || definitions.size() == 0){
				throw new BeanDefinitionNotFoundException(String.format("%s#%s",beanType.getName(),beanName));
			}
			if(!definitions.containsKey(beanName)){
				throw new BeanDefinitionNotFoundException(String.format("%s#%s",beanType.getName(),beanName));
			}
			return definitions.get(beanName);
		}
	}

	@Override
	public BeanDefinition getBeanDefinition(Class<?> beanType) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		Map<String,BeanDefinition> definitions = this.getBeanDefinitions(beanType);
		if(null == definitions || definitions.size() == 0){
			throw new BeanDefinitionNotFoundException(beanType.getName());
		}
		if(definitions.size() != 1){
			throw new BeanDefinitionMultipleException(beanType.getName());
		}
		return definitions.values().iterator().next();
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws BeanDefinitionNotFoundException,BeanDefinitionMultipleException{
		Map<String,BeanDefinition> definitions = this.getBeanDefinitions(beanName);
		if(null == definitions || definitions.size() == 0){
			throw new BeanDefinitionNotFoundException(beanName);
		}
		if(definitions.size() != 1){
			throw new BeanDefinitionMultipleException(beanName);
		}
		return definitions.values().iterator().next();
	}

	@Override
	public Map<String,BeanDefinition> getBeanDefinitions(final String beanName) throws BeanDefinitionNotFoundException{
		if(StringUtils.isEmpty(beanName)){
			throw new IllegalArgumentException("bean name must not be null");
		}
		final Map<String,BeanDefinition> definitions = this.getAllBeanDefinitions();
		if(!definitions.containsKey(beanName)){
			throw new BeanDefinitionNotFoundException(beanName);
		}
		return new HashMap<String,BeanDefinition>() {
			private static final long serialVersionUID = -215112969001212256L;
			{
				super.put(beanName,definitions.get(beanName));
			}
		};
	}

	@Override
	public Map<String,BeanDefinition> getBeanDefinitions(Class<?> beanType) throws BeanDefinitionNotFoundException{
		if(null == beanType){
			throw new IllegalArgumentException("bean type must not be null");
		}
		Map<String,BeanDefinition> result = new HashMap<String,BeanDefinition>();
		Iterator<Entry<String,BeanDefinition>> iter = this.getBeanDefinitionRegistry().iterator();
		while(iter.hasNext()){
			Entry<String,BeanDefinition> entry = iter.next();
			if(entry.getValue().isAssignable(beanType)){
				result.put(entry.getKey(),entry.getValue());
			}
		}
		if(result.size() == 0){
			throw new BeanDefinitionNotFoundException(beanType.getName());
		}
		return result;
	}

	@Override
	public Map<String,BeanDefinition> getAllBeanDefinitions() throws BeanDefinitionNotFoundException{
		return this.getBeanDefinitionRegistry().getAllBeanDefinitions();
	}

	@Override
	protected BeanDefinitionFactoryEvent createEvent(){
		return new DefaultBeanDefinitionFactoryEvent(this);
	}

	@Override
	public BeanDefinition createBeanDefinition(Class<?> beanType,String beanName){
		return this.createBeanDefinition(beanType,beanName,null);
	}

	@Override
	public BeanDefinition createBeanDefinition(Class<?> beanType){
		return this.createBeanDefinition(beanType,null,null);
	}

	@Override
	public BeanDefinition createBeanDefinition(Object beanInstance){
		return this.createBeanDefinition(beanInstance,null);
	}

	@Override
	public BeanDefinition createBeanDefinition(BeanDefinition parentBeanDefinition,Method createBeanMethod,String beanName){
		return this.createBeanDefinition(parentBeanDefinition,createBeanMethod,beanName,null);
	}

	@Override
	public BeanDefinition createBeanDefinition(BeanDefinition parentBeanDefinition,Method createBeanMethod){
		return this.createBeanDefinition(parentBeanDefinition,createBeanMethod,null,null);
	}

}