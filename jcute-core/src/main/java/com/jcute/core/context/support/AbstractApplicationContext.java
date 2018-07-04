package com.jcute.core.context.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.bean.exception.BeanDefinitionMultipleException;
import com.jcute.core.bean.exception.BeanDefinitionNotFoundException;
import com.jcute.core.config.ConfigSourceManager;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.context.ApplicationContextEvent;
import com.jcute.core.context.ApplicationContextListener;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.util.GenericUtils;

public abstract class AbstractApplicationContext extends AbstractStable<ApplicationContextEvent,ApplicationContextListener> implements ApplicationContext{

	private static final Logger logger = LoggerFactory.getLogger(AbstractApplicationContext.class);

	protected BeanDefinitionFactory beanDefinitionFactory;
	protected PluginManager pluginManager;

	public AbstractApplicationContext(){
		this.pluginManager = this.createPluginManager(this);
		this.beanDefinitionFactory = this.createBeanDefinitionFactory();
	}

	@Override
	public ConfigSourceManager getConfigSourceManager(){
		return this.beanDefinitionFactory.getConfigSourceManager();
	}

	@Override
	public BeanDefinitionFactory getBeanDefinitionFactory(){
		return this.beanDefinitionFactory;
	}

	@Override
	public BeanDefinitionRegistry getBeanDefinitionRegistry(){
		return this.beanDefinitionFactory.getBeanDefinitionRegistry();
	}

	@Override
	public BeanDefinitionResolver getBeanDefinitionResolver(){
		return this.beanDefinitionFactory.getBeanDefinitionResolver();
	}

	@Override
	public <T>T getBean(Class<?> beanType,String beanName){
		try{
			BeanDefinition beanDefinition = this.beanDefinitionFactory.getBeanDefinition(beanType,beanName);
			return GenericUtils.parse(beanDefinition.getBeanInstance());
		}catch(BeanDefinitionNotFoundException e){
			logger.warn(e.getMessage(),e);
		}catch(BeanDefinitionMultipleException e){
			logger.warn(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public <T>T getBean(Class<?> beanType){
		try{
			BeanDefinition beanDefinition = this.beanDefinitionFactory.getBeanDefinition(beanType);
			return GenericUtils.parse(beanDefinition.getBeanInstance());
		}catch(BeanDefinitionNotFoundException e){
			logger.warn(e.getMessage(),e);
		}catch(BeanDefinitionMultipleException e){
			logger.warn(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public <T>T getBean(String beanName){
		try{
			BeanDefinition beanDefinition = this.beanDefinitionFactory.getBeanDefinition(beanName);
			return GenericUtils.parse(beanDefinition.getBeanInstance());
		}catch(BeanDefinitionNotFoundException e){
			logger.warn(e.getMessage(),e);
		}catch(BeanDefinitionMultipleException e){
			logger.warn(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public <T>Map<String,T> getBeans(Class<?> beanType){
		Map<String,T> result = new HashMap<String,T>();
		try{
			Map<String,BeanDefinition> mappings = this.beanDefinitionFactory.getBeanDefinitions(beanType);
			if(null != mappings && mappings.size() > 0){
				for(Entry<String,BeanDefinition> entry : mappings.entrySet()){
					T t = GenericUtils.parse(entry.getValue().getBeanInstance());
					result.put(entry.getKey(),t);
				}
			}
		}catch(BeanDefinitionNotFoundException e){
			logger.warn(e.getMessage(),e);
		}
		return result;
	}

	@Override
	protected ApplicationContextEvent createEvent(){
		return new ApplicationContextEvent() {
			@Override
			public BeanDefinitionResolver getBeanDefinitionResolver(){
				return getBeanDefinitionResolver();
			}

			@Override
			public BeanDefinitionRegistry getBeanDefinitionRegistry(){
				return getBeanDefinitionRegistry();
			}

			@Override
			public BeanDefinitionFactory getBeanDefinitionFactory(){
				return getBeanDefinitionFactory();
			}

			@Override
			public ApplicationContext getApplicationContext(){
				return AbstractApplicationContext.this;
			}
		};
	}

	@Override
	protected void doStart() throws Exception{
		long time = System.currentTimeMillis();
		try{
			this.pluginManager.beforeStart();
		}catch(Exception e){
			logger.warn("start plugin manager failed {}",e.getMessage(),e);
			throw e;
		}
		this.beforeDoStart(this.beanDefinitionFactory);
		try{
			this.pluginManager.start();
		}catch(Exception e){
			logger.warn("start plugin manager failed {}",e.getMessage(),e);
			throw e;
		}
		this.beanDefinitionFactory.getBeanDefinitionRegistry().attachBeanDefinition(this.beanDefinitionFactory.createBeanDefinition(this));
		this.beanDefinitionFactory.start();
		logger.info("Application Context Start Success , Time Of Use {} Millisecond",System.currentTimeMillis() - time);
	}

	@Override
	protected void doClose() throws Exception{
		long time = System.currentTimeMillis();
		try{
			this.pluginManager.beforeClose();
		}catch(Exception e){
			logger.warn("close plugin manager failed {}",e.getMessage(),e);
			throw e;
		}
		this.beforeDoClose(this.beanDefinitionFactory);
		try{
			this.pluginManager.close();
		}catch(Exception e){
			logger.warn("close plugin manager failed {}",e.getMessage(),e);
			throw e;
		}
		this.beanDefinitionFactory.close();
		logger.info("Application Context Close Success , Time Of Use {} Millisecond",System.currentTimeMillis() - time);
	}

	protected abstract void beforeDoStart(BeanDefinitionFactory beanDefinitionFactory);

	protected abstract void beforeDoClose(BeanDefinitionFactory beanDefinitionFactory);

	protected abstract PluginManager createPluginManager(ApplicationContext applicationContext);

	protected abstract BeanDefinitionFactory createBeanDefinitionFactory();

}