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
import com.jcute.core.context.ApplicationContextListener;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.toolkit.proxy.ProxyManager;
import com.jcute.core.util.GenericUtils;

public abstract class AbstractApplicationContext extends AbstractStable<ApplicationContext,ApplicationContextListener> implements ApplicationContext{

	private static final Logger logger = LoggerFactory.getLogger(AbstractApplicationContext.class);

	private BeanDefinitionFactory beanDefinitionFactory;
	private BeanDefinitionRegistry beanDefinitionRegistry;
	private BeanDefinitionResolver beanDefinitionResolver;
	private ConfigSourceManager configSourceManager;
	private PluginManager pluginManager;
	private ProxyManager proxyManager;

	protected AbstractApplicationContext(){
		this.proxyManager = this.createProxyManager();
		this.pluginManager = this.createPluginManager();
		this.configSourceManager = this.createConfigSourceManager();
		this.beanDefinitionFactory = this.createBeanDefinitionFactory(this);
		this.beanDefinitionRegistry = this.createBeanDefinitionRegistry(this.beanDefinitionFactory);
		this.beanDefinitionResolver = this.createBeanDefinitionResolver(this.beanDefinitionFactory,this.beanDefinitionRegistry);
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
	public ProxyManager getProxyManager(){
		return this.proxyManager;
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
		long time = System.currentTimeMillis();
		try{
			this.initPlugins(this.pluginManager);
			this.pluginManager.start();
		}catch(Exception e){
			logger.warn("start plugin manager failed {}",e.getMessage(),e);
			throw e;
		}
		try{
			this.configSourceManager.start();
		}catch(Exception e){
			logger.warn("start config source manager failed {}",e.getMessage(),e);
			throw e;
		}
		this.getBeanDefinitionRegistry().attachBeanDefinition(this.getBeanDefinitionFactory().createBeanDefinition(this));
		this.beforeDoStart();
		try{
			this.beanDefinitionRegistry.start();
		}catch(Exception e){
			logger.warn("start bean definition registry failed {}",e.getMessage(),e);
			throw e;
		}
		try{
			this.beanDefinitionFactory.start();
		}catch(Exception e){
			logger.warn("start bean definition factory failed {}",e.getMessage(),e);
			throw e;
		}
		logger.info("Application Context Start Success , Time Of Use {} Millisecond",System.currentTimeMillis() - time);
	}

	@Override
	protected void doClose() throws Exception{
		long time = System.currentTimeMillis();
		try{
			this.pluginManager.close();
		}catch(Exception e){
			logger.warn("close plugin manager failed {}",e.getMessage(),e);
			throw e;
		}
		try{
			this.configSourceManager.close();
		}catch(Exception e){
			logger.warn("close config source manager failed {}",e.getMessage(),e);
			throw e;
		}
		this.beforeDoClose();
		try{
			this.beanDefinitionRegistry.close();
		}catch(Exception e){
			logger.warn("close bean definition registry failed {}",e.getMessage(),e);
			throw e;
		}
		try{
			this.beanDefinitionFactory.close();
		}catch(Exception e){
			logger.warn("close bean definition factory failed {}",e.getMessage(),e);
			throw e;
		}
		logger.info("Application Context Close Success , Time Of Use {} Millisecond",System.currentTimeMillis() - time);
	}

	protected void beforeDoStart(){
	};

	protected void beforeDoClose(){
	};

	protected abstract BeanDefinitionFactory createBeanDefinitionFactory(ApplicationContext applicationContext);

	protected abstract BeanDefinitionRegistry createBeanDefinitionRegistry(BeanDefinitionFactory beanDefinitionFactory);

	protected abstract BeanDefinitionResolver createBeanDefinitionResolver(BeanDefinitionFactory beanDefinitionFactory,BeanDefinitionRegistry beanDefinitionRegistry);

	protected abstract ConfigSourceManager createConfigSourceManager();

	protected abstract PluginManager createPluginManager();

	protected abstract ProxyManager createProxyManager();
	
	protected abstract void initPlugins(PluginManager pluginManager);

}