package com.jcute.plugin.cache.support;

import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.util.GenericUtils;
import com.jcute.core.util.StringUtils;
import com.jcute.plugin.cache.Cache;
import com.jcute.plugin.cache.CacheEvent;
import com.jcute.plugin.cache.CacheListener;
import com.jcute.plugin.cache.CacheValueWrapper;

public abstract class AbstractCache extends AbstractStable<CacheEvent,CacheListener> implements Cache{

	private String cacheName;

	public AbstractCache(String cacheName){
		if(StringUtils.isEmpty(cacheName)){
			throw new IllegalArgumentException("cache name must not be null");
		}
		this.cacheName = cacheName;
	}

	@Override
	public String getCacheName(){
		return this.cacheName;
	}

	@Override
	public void putValue(Object key,Object value){
		this.putValue(key,value,0);
	}

	@Override
	public <T>T getValue(Object key){
		CacheValueWrapper cacheValueWrapper = this.getValueWrapper(key);
		if(null == cacheValueWrapper || cacheValueWrapper.isNullValue()){
			return null;
		}
		return GenericUtils.parse(cacheValueWrapper.getValue());
	}

	@Override
	protected CacheEvent createEvent(){
		return new CacheEvent() {
			@Override
			public Cache getCache(){
				return AbstractCache.this;
			}
		};
	}
	
}