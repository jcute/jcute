package com.jcute.plugin.cache.support;

import com.jcute.plugin.cache.Cache;
import com.jcute.plugin.cache.CacheKeyPolicy;

public class DefaultCacheManager extends AbstractCacheManager{

	public DefaultCacheManager() throws Exception{
		super();
	}

	@Override
	protected Cache createCache(String cacheName){
		return new DefaultCache(cacheName);
	}

	@Override
	protected CacheKeyPolicy createCacheKeyPolicy(){
		return new DefaultCacheKeyPolicy();
	}

}