package com.jcute.plugin.cache.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.plugin.cache.CacheValueWrapper;

public class DefaultCache extends AbstractCache{

	private static final CacheValueWrapper NULL_CACHE_VALUE_WRAPPER = new DefaultCacheValueWrapper(null);

	private Map<Object,CacheValueWrapper> store = new ConcurrentHashMap<Object,CacheValueWrapper>();
	private Map<Object,Duration> duras = new ConcurrentHashMap<Object,Duration>();

	public DefaultCache(String cacheName){
		super(cacheName);
	}

	@Override
	public Object getCacheNative(){
		return this.store;
	}

	@Override
	public boolean containsKey(Object key){
		if(this.store.containsKey(key)){
			if(this.duras.containsKey(key)){
				if(this.duras.get(key).isScrap()){
					this.duras.remove(key);
					this.store.remove(key);
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}
		return false;
	}

	@Override
	public void putValue(Object key,Object value,long expiry){
		CacheValueWrapper cacheValueWrapper = this.createCacheValueWrapper(value);
		this.store.put(key,cacheValueWrapper);
		if(expiry > 0){
			this.duras.put(key,new Duration(System.currentTimeMillis(),expiry));
		}
	}

	@Override
	public CacheValueWrapper getValueWrapper(Object key){
		CacheValueWrapper result = NULL_CACHE_VALUE_WRAPPER;
		if(this.store.containsKey(key)){
			result = this.store.get(key);
		}
		Duration duration = this.duras.get(key);
		if(null != duration){
			if(duration.isScrap()){
				result = NULL_CACHE_VALUE_WRAPPER;
				this.store.remove(key);
				this.duras.remove(key);
			}
		}
		return result;
	}

	@Override
	public void evict(Object key){
		this.store.remove(key);
	}

	@Override
	public void clear(){
		this.store.clear();
		this.duras.clear();
	}

	@Override
	protected void doStart() throws Exception{
		this.clear();
	}

	@Override
	protected void doClose() throws Exception{
		this.clear();
	}

	protected CacheValueWrapper createCacheValueWrapper(Object value){
		return new DefaultCacheValueWrapper(value);
	}

	protected static class Duration{
		private long start;
		private long split;
		public Duration(long start,long split){
			this.start = start;
			this.split = split;
		}
		public boolean isScrap(){
			return System.currentTimeMillis() > this.start + this.split;
		}
	}

}