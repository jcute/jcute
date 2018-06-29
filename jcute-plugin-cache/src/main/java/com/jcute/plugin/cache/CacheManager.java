package com.jcute.plugin.cache;

import java.util.Map;
import java.util.Set;

import com.jcute.core.toolkit.cycle.Stable;

public interface CacheManager extends Stable<CacheManagerEvent,CacheManagerListener>{

	public Cache getCache(String cacheName);

	public Set<String> getCacheNames();

	public Map<String,Cache> getCaches();

	public void setCacheNames(String... cacheNames);
	
	public void setCacheKeyPolicy(CacheKeyPolicy cacheKeyPolicy);
	
	public CacheKeyPolicy getCacheKeyPolicy();
	
	public void attachCacheCreateListener(CacheManagerListener listener);
	
	public void detachCacheCreateListener(CacheManagerListener listener);
	
}