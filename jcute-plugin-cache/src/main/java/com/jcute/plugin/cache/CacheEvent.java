package com.jcute.plugin.cache;

import com.jcute.core.toolkit.cycle.StableEvent;

public interface CacheEvent extends StableEvent{
	
	public Cache getCache();
	
}