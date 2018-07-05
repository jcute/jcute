package com.jcute.core.cont.support;

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
import com.jcute.core.cont.ApplicationContext;
import com.jcute.core.cont.ApplicationContextListener;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.util.GenericUtils;

public abstract class AbstractApplicationContext extends AbstractStable<ApplicationContext,ApplicationContextListener> implements ApplicationContext{

	private static final Logger logger = LoggerFactory.getLogger(AbstractApplicationContext.class);

	private BeanDefinitionFactory beanDefinitionFactory;
	private BeanDefinitionRegistry beanDefinitionRegistry;
	private BeanDefinitionResolver beanDefinitionResolver;
	private ConfigSourceManager configSourceManager;
	private PluginManager pluginManager;

	protected AbstractApplicationContext(){
		this.pluginManager = this.createPluginManager();
		this.configSourceManager = this.createConfigSourceManager();
		this.beanDefinitionResolver = this.createBeanDefinitionResolver();
		this.beanDefinitionRegistry = this.createBeanDefinitionRegistry();
		this.beanDefinitionFactory = this.createBeanDefinitionFactory();
	}

	@Override
	public BeanDefinitionFactory getBeanDefinitionFactory(){
		return this.beanDefinitionFactory;
	}

	@Override
	public BeanDefinitionRegistry getBeanDefinitionRegistry(){
		return this.beanDefinitionRegistry;
	}

	@Override
	public BeanDefinitionResolver getBeanDefinitionResolver(){
		return this.beanDefinitionResolver;
	}

	@Override
	public ConfigSourceManager getConfigSourceManager(){
		return this.configSourceManager;
	}

	@Override
	public PluginManager getPluginManager(){
		return this.pluginManager;
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
	protected ApplicationContext createEvent(){
		return this;
	}

	@Override
	protected void doStart() throws Exception{
		this.pluginManager.start();
		this.beanDefinitionRegistry.start();
		this.beanDefinitionFactory.start();
	}

	@Override
	protected void doClose() throws Exception{
		this.beanDefinitionFactory.close();
		this.beanDefinitionRegistry.close();
		this.pluginManager.close();
	}

	protected abstract BeanDefinitionFactory createBeanDefinitionFactory();

	protected abstract BeanDefinitionRegistry createBeanDefinitionRegistry();

	protected abstract BeanDefinitionResolver createBeanDefinitionResolver();

	protected abstract ConfigSourceManager createConfigSourceManager();

	protected abstract PluginManager createPluginManager();

}