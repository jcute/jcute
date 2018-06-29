package com.jcute.core.bean.support;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionRegistryEvent;
import com.jcute.core.bean.BeanDefinitionRegistryListener;
import com.jcute.core.bean.exception.BeanDefinitionExistsException;
import com.jcute.core.toolkit.cycle.EventableCallBack;
import com.jcute.core.toolkit.cycle.support.AbstractStable;

public abstract class AbstractBeanDefinitionRegistry extends AbstractStable<BeanDefinitionRegistryEvent,BeanDefinitionRegistryListener> implements BeanDefinitionRegistry{

	protected static final String BEAN_DEFINITION_ADD_SUCCESS = "BEAN_DEFINITION_ADD_SUCCESS";
	protected static final String BEAN_DEFINITION_DEL_SUCCESS = "BEAN_DEFINITION_DEL_SUCCESS";

	protected BeanDefinitionFactory beanDefinitionFactory;
	protected Map<String,BeanDefinition> beanDefinitions;
	protected Set<BeanDefinition> sortBeanDefinitions;

	public AbstractBeanDefinitionRegistry(BeanDefinitionFactory beanDefinitionFactory){
		if(null == beanDefinitionFactory){
			throw new IllegalArgumentException("bean definition factory must not be null");
		}
		this.beanDefinitionFactory = beanDefinitionFactory;
		this.beanDefinitions = new ConcurrentHashMap<String,BeanDefinition>();
		this.sortBeanDefinitions = new LinkedHashSet<BeanDefinition>();
	}

	@Override
	public BeanDefinitionFactory getBeanDefinitionFactory(){
		return this.beanDefinitionFactory;
	}

	@Override
	public Map<String,BeanDefinition> getAllBeanDefinitions(){
		return Collections.unmodifiableMap(this.beanDefinitions);
	}

	@Override
	public void attachBeanDefinition(final BeanDefinition beanDefinition) throws BeanDefinitionExistsException{
		if(null == beanDefinition){
			throw new IllegalArgumentException("bean definition must not be null");
		}
		if(this.containsBeanDefinition(beanDefinition)){
			throw new BeanDefinitionExistsException(beanDefinition.toString());
		}
		this.beanDefinitions.put(beanDefinition.getBeanName(),beanDefinition);
		this.fireEvent(BEAN_DEFINITION_ADD_SUCCESS,new EventableCallBack<BeanDefinitionRegistryEvent>() {
			@Override
			public BeanDefinitionRegistryEvent callback(BeanDefinitionRegistryEvent event){
				event.setBeanDefinition(beanDefinition);
				return event;
			}
		});
	}

	@Override
	public boolean containsBeanDefinition(BeanDefinition beanDefinition){
		return this.containsBeanDefinition(beanDefinition.getBeanName());
	}

	@Override
	public boolean containsBeanDefinition(String beanName){
		return this.beanDefinitions.containsKey(beanName);
	}

	@Override
	public Iterator<Entry<String,BeanDefinition>> iterator(){
		return this.beanDefinitions.entrySet().iterator();
	}

	@Override
	public void attachBeanDefinitionAddSuccessListener(BeanDefinitionRegistryListener listener){
		this.attachListener(BEAN_DEFINITION_ADD_SUCCESS,listener);
	}

	@Override
	public void detachBeanDefinitionAddSuccessListener(BeanDefinitionRegistryListener listener){
		this.detachListener(BEAN_DEFINITION_ADD_SUCCESS,listener);
	}

	@Override
	public void attachBeanDefinitionDelSuccessListener(BeanDefinitionRegistryListener listener){
		this.attachListener(BEAN_DEFINITION_DEL_SUCCESS,listener);
	}

	@Override
	public void detachBeanDefinitionDelSuccessListener(BeanDefinitionRegistryListener listener){
		this.detachListener(BEAN_DEFINITION_DEL_SUCCESS,listener);
	}

	@Override
	protected BeanDefinitionRegistryEvent createEvent(){
		return new DefaultBeanDefinitionRegistryEvent(this.beanDefinitionFactory);
	}

	@Override
	protected void doStart() throws Exception{
		this.sortBeanDefinitions.clear();
		this.sortBeanDefinitions.addAll(this.beanDefinitionFactory.getBeanDefinitionResolver().getSortBeanDefinitions(this.beanDefinitions));
		for(BeanDefinition beanDefinition : this.sortBeanDefinitions){
			beanDefinition.getBeanInstanceHandler().onCreate();
		}
		for(BeanDefinition beanDefinition : this.sortBeanDefinitions){
			beanDefinition.getBeanInstanceHandler().onInject();
		}
		for(BeanDefinition beanDefinition : this.sortBeanDefinitions){
			beanDefinition.getBeanInstanceHandler().onInitial();
		}
	}

	@Override
	protected void doClose() throws Exception{
		for(BeanDefinition beanDefinition : this.sortBeanDefinitions){
			beanDefinition.getBeanInstanceHandler().onRelease();
		}
		for(BeanDefinition beanDefinition : this.sortBeanDefinitions){
			beanDefinition.getBeanInstanceHandler().onDestory();
		}
		for(BeanDefinition beanDefinition : this.sortBeanDefinitions){
			final BeanDefinition eventBeanDefinition = beanDefinition;
			this.fireEvent(BEAN_DEFINITION_DEL_SUCCESS,new EventableCallBack<BeanDefinitionRegistryEvent>() {
				@Override
				public BeanDefinitionRegistryEvent callback(BeanDefinitionRegistryEvent event){
					event.setBeanDefinition(eventBeanDefinition);
					return event;
				}
			});
		}
		this.beanDefinitions.clear();
		this.sortBeanDefinitions.clear();
	}

}