package com.jcute.plugin.cache.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.jcute.core.annotation.Autowired;
import com.jcute.core.annotation.Interceptor;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.toolkit.proxy.ProxyChain;
import com.jcute.core.toolkit.proxy.ProxyFactory;
import com.jcute.core.util.AnnotationUtils;
import com.jcute.core.util.StringUtils;
import com.jcute.plugin.cache.Cache;
import com.jcute.plugin.cache.CacheKeyPolicy;
import com.jcute.plugin.cache.CacheManager;
import com.jcute.plugin.cache.EnableCacheManagerPlugin;
import com.jcute.plugin.cache.annotation.CacheClear;
import com.jcute.plugin.cache.annotation.CacheEvict;
import com.jcute.plugin.cache.annotation.CacheKey;
import com.jcute.plugin.cache.annotation.CachePut;
import com.jcute.plugin.cache.annotation.Cacheable;

@Interceptor(annotations = Cacheable.class)
public class CacheableInterceptor implements ProxyFactory{

	private static final Logger logger = LoggerFactory.getLogger(CacheableInterceptor.class);

	@Autowired
	private ApplicationContext applicationContext;

	private EnableCacheManagerPlugin enableCacheManagerPlugin;

	public CacheableInterceptor(EnableCacheManagerPlugin enableCacheManagerPlugin){
		this.enableCacheManagerPlugin = enableCacheManagerPlugin;
	}

	@Override
	public Object execute(ProxyChain proxyChain) throws Throwable{
		Class<?> targetClass = proxyChain.getTargetClass();
		Method targetMethod = proxyChain.getTargetMethod();
		Object[] targetParams = proxyChain.getTargetParameterDatas();
		if(this.intercept(targetMethod)){
			if(AnnotationUtils.hasAnnotation(targetMethod,CachePut.class)){
				CacheManager cacheManager = this.searchCacheManager(targetClass,targetMethod,CachePut.class);
				if(null == cacheManager){
					return proxyChain.doProxyChain();
				}
				Cache cache = this.searchCache(cacheManager,targetClass,targetMethod,CachePut.class);
				if(null == cache){
					return proxyChain.doProxyChain();
				}
				CachePut cachePut = AnnotationUtils.getAnnotation(targetMethod,CachePut.class);
				Object cacheKey = this.resolveCacheKey(cacheManager,targetClass,targetMethod,targetParams,CachePut.class);
				if(null == cacheKey){
					return proxyChain.doProxyChain();
				}
				if(cache.containsKey(cacheKey)){
					logger.debug("find data from cache {}.{}",cache.getCacheName(),cacheKey.toString());
					return cache.getValue(cacheKey);
				}
				Object result = proxyChain.doProxyChain();
				cache.putValue(cacheKey,result,cachePut.cacheExpiry());
				return result;
			}
			if(AnnotationUtils.hasAnnotation(targetMethod,CacheEvict.class)){
				CacheManager cacheManager = this.searchCacheManager(targetClass,targetMethod,CacheEvict.class);
				if(null == cacheManager){
					return proxyChain.doProxyChain();
				}
				Cache cache = this.searchCache(cacheManager,targetClass,targetMethod,CacheEvict.class);
				if(null == cache){
					return proxyChain.doProxyChain();
				}
				Object cacheKey = this.resolveCacheKey(cacheManager,targetClass,targetMethod,targetParams,CacheEvict.class);
				if(null == cacheKey){
					return proxyChain.doProxyChain();
				}
				if(!cache.containsKey(cacheKey)){
					return proxyChain.doProxyChain();
				}
				cache.evict(cacheKey);
				logger.debug("evict data from cache {}.{}",cache.getCacheName(),cacheKey.toString());
				return proxyChain.doProxyChain();
			}
			if(AnnotationUtils.hasAnnotation(targetMethod,CacheClear.class)){
				CacheManager cacheManager = this.searchCacheManager(targetClass,targetMethod,CacheClear.class);
				if(null == cacheManager){
					return proxyChain.doProxyChain();
				}
				Cache cache = this.searchCache(cacheManager,targetClass,targetMethod,CacheClear.class);
				if(null == cache){
					return proxyChain.doProxyChain();
				}
				cache.clear();
				logger.debug("clear data from cache {}",cache.getCacheName());
				return proxyChain.doProxyChain();
			}
		}
		return proxyChain.doProxyChain();
	}

	protected Object resolveCacheKey(CacheManager cacheManager,Class<?> targetClass,Method targetMethod,Object[] targetParams,Class<? extends Annotation> cacheType){
		String expression = null;
		if(cacheType.equals(CachePut.class) && AnnotationUtils.hasAnnotation(targetMethod,CachePut.class)){
			CachePut cachePut = AnnotationUtils.getAnnotation(targetMethod,CachePut.class);
			if(!StringUtils.isEmpty(cachePut.cacheKey())){
				expression = cachePut.cacheKey();
			}
		}
		if(cacheType.equals(CacheEvict.class) && AnnotationUtils.hasAnnotation(targetMethod,CacheEvict.class)){
			CacheEvict cacheEvict = AnnotationUtils.getAnnotation(targetMethod,CacheEvict.class);
			if(!StringUtils.isEmpty(cacheEvict.cacheKey())){
				expression = cacheEvict.cacheKey();
			}
		}
		if(StringUtils.isEmpty(expression)){
			return null;
		}
		Map<String,Object> context = new HashMap<String,Object>();
		context.put("targetArguments",targetParams);
		context.put("targetClass",targetClass);
		context.put("targetMethod",targetClass);
		if(null != targetParams && targetParams.length > 0){
			Annotation[] annotations = AnnotationUtils.getAnnotation(targetMethod.getParameterTypes(),targetMethod.getParameterAnnotations(),CacheKey.class);
			for(int i = 0;i < annotations.length;i++){
				if(null == annotations[i]){
					continue;
				}
				CacheKey ck = (CacheKey)annotations[i];
				if(StringUtils.isEmpty(ck.value())){
					continue;
				}
				context.put(ck.value(),targetParams[i]);
			}
		}
		CacheKeyPolicy cacheKeyPolicy = cacheManager.getCacheKeyPolicy();
		Object result = cacheKeyPolicy.getCacheKey(context,expression);
		return null == result ? expression : result;
	}

	protected Cache searchCache(CacheManager cacheManager,Class<?> targetClass,Method targetMethod,Class<? extends Annotation> cacheType){
		if(null == cacheManager){
			return null;
		}
		Cacheable cacheable = AnnotationUtils.getAnnotation(targetClass,Cacheable.class);
		String cacheName = cacheable.value();
		if(cacheType.equals(CachePut.class) && AnnotationUtils.hasAnnotation(targetMethod,CachePut.class)){
			CachePut cachePut = AnnotationUtils.getAnnotation(targetMethod,CachePut.class);
			if(!StringUtils.isEmpty(cachePut.value())){
				cacheName = cachePut.value();
			}
		}
		if(cacheType.equals(CacheEvict.class) && AnnotationUtils.hasAnnotation(targetMethod,CacheEvict.class)){
			CacheEvict cacheEvict = AnnotationUtils.getAnnotation(targetMethod,CacheEvict.class);
			if(!StringUtils.isEmpty(cacheEvict.value())){
				cacheName = cacheEvict.value();
			}
		}
		if(cacheType.equals(CacheClear.class) && AnnotationUtils.hasAnnotation(targetMethod,CacheClear.class)){
			CacheClear cacheClear = AnnotationUtils.getAnnotation(targetMethod,CacheClear.class);
			if(!StringUtils.isEmpty(cacheClear.value())){
				cacheName = cacheClear.value();
			}
		}
		if(StringUtils.isEmpty(cacheName)){
			logger.debug("found {} and missing cache name",cacheType.getName());
			return null;
		}
		return cacheManager.getCache(cacheName);
	}

	protected CacheManager searchCacheManager(Class<?> targetClass,Method targetMethod,Class<? extends Annotation> cacheType){
		String cacheManagerName = null;
		Cacheable cacheable = AnnotationUtils.getAnnotation(targetClass,Cacheable.class);
		if(!StringUtils.isEmpty(cacheable.cacheManager())){
			cacheManagerName = cacheable.cacheManager();
		}
		if(cacheType.equals(CacheEvict.class) && AnnotationUtils.hasAnnotation(targetMethod,CacheEvict.class)){
			CacheEvict cacheEvict = AnnotationUtils.getAnnotation(targetMethod,CacheEvict.class);
			if(!StringUtils.isEmpty(cacheEvict.cacheManager())){
				cacheManagerName = cacheEvict.cacheManager();
			}
		}
		if(cacheType.equals(CachePut.class) && AnnotationUtils.hasAnnotation(targetMethod,CachePut.class)){
			CachePut cachePut = AnnotationUtils.getAnnotation(targetMethod,CachePut.class);
			if(!StringUtils.isEmpty(cachePut.cacheManager())){
				cacheManagerName = cachePut.cacheManager();
			}
		}
		if(cacheType.equals(CacheClear.class) && AnnotationUtils.hasAnnotation(targetMethod,CacheClear.class)){
			CacheClear cacheClear = AnnotationUtils.getAnnotation(targetMethod,CacheClear.class);
			if(StringUtils.isEmpty(cacheClear.cacheManager())){
				cacheManagerName = cacheClear.cacheManager();
			}
		}

		if(StringUtils.isEmpty(cacheManagerName)){
			return this.getDefaultCacheManager();
		}
		return applicationContext.getBean(CacheManager.class,cacheManagerName);
	}

	protected boolean intercept(Method method){
		if(AnnotationUtils.hasAnnotation(method,CachePut.class) || AnnotationUtils.hasAnnotation(method,CacheEvict.class) || AnnotationUtils.hasAnnotation(method,CacheClear.class)){
			return true;
		}
		return false;
	}

	protected CacheManager getDefaultCacheManager(){
		return this.enableCacheManagerPlugin.getDefaultCacheManager();
	}

}