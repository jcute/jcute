package com.jcute.plugin.cache;

import com.jcute.core.toolkit.cycle.StableEvent;

public interface CacheManagerEvent extends StableEvent{
	
	public CacheManager getCacheManager();
	
	public Cache getCache();
	
}