package com.jcute.plugin.cache;

import com.jcute.core.toolkit.cycle.Stable;

public interface Cache extends Stable<CacheEvent,CacheListener>{

	public String getCacheName();

	public Object getCacheNative();

	public boolean containsKey(Object key);

	public void putValue(Object key,Object value);

	public void putValue(Object key,Object value,long expiry);

	public <T> T getValue(Object key);

	public CacheValueWrapper getValueWrapper(Object key);

	public void evict(Object key);

	public void clear();

}