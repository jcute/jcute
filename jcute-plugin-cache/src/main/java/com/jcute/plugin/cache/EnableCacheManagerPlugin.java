package com.jcute.plugin.cache;

import java.lang.annotation.Annotation;

import com.jcute.core.bean.BeanDefinition;
import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.plugin.Plugin;
import com.jcute.core.util.StringUtils;
import com.jcute.plugin.cache.interceptor.CacheableInterceptor;
import com.jcute.plugin.cache.support.DefaultCacheManager;

public class EnableCacheManagerPlugin extends Plugin{

	private static final String DEFAULT_CACHE_MANAGER_NAME = "defaultCacheManager";

	private CacheManager defaultCacheManager;

	public EnableCacheManagerPlugin(ApplicationContext applicationContext,Annotation annotation){
		super(applicationContext,annotation);
	}
	
	@Override
	protected void onStart() throws Exception{

		final EnableCacheManager enableCacheManager = this.getAnnotation();
		final ApplicationContext applicationContext = this.getApplicationContext();
		final BeanDefinitionFactory beanDefinitionFactory = applicationContext.getBeanDefinitionFactory();
		final BeanDefinitionRegistry beanDefinitionRegistry = applicationContext.getBeanDefinitionRegistry();
		final String cacheManagerName = enableCacheManager.value();

		// 如果未指定cacheManager名称,默认创建系统内置的cacheManager,并注入到容器中
		if(StringUtils.isEmpty(cacheManagerName)){
			this.defaultCacheManager = this.createDefaultCacheManager();
			BeanDefinition cacheManagerBeanDefinition = beanDefinitionFactory.createBeanDefinition(this.defaultCacheManager,DEFAULT_CACHE_MANAGER_NAME);
			beanDefinitionRegistry.attachBeanDefinition(cacheManagerBeanDefinition);
		}
		
		CacheableInterceptor cacheableInterceptor = new CacheableInterceptor(this);
		BeanDefinition cacheableInterceptorBeanDefinition = beanDefinitionFactory.createBeanDefinition(cacheableInterceptor);
		beanDefinitionRegistry.attachBeanDefinition(cacheableInterceptorBeanDefinition);
		
	}
	
	public CacheManager getDefaultCacheManager(){
		if(null != this.defaultCacheManager){
			return this.defaultCacheManager;
		}
		EnableCacheManager enableCacheManager = this.getAnnotation();
		ApplicationContext applicationContext = this.getApplicationContext();
		return applicationContext.getBean(CacheManager.class,enableCacheManager.value());
	}
	
	private CacheManager createDefaultCacheManager() throws Exception{
		return new DefaultCacheManager();
	}

}