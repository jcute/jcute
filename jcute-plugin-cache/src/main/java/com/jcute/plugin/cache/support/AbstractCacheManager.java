package com.jcute.plugin.cache.support;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.toolkit.cycle.EventableCallBack;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.util.StringUtils;
import com.jcute.plugin.cache.Cache;
import com.jcute.plugin.cache.CacheKeyPolicy;
import com.jcute.plugin.cache.CacheManager;
import com.jcute.plugin.cache.CacheManagerEvent;
import com.jcute.plugin.cache.CacheManagerListener;

public abstract class AbstractCacheManager extends AbstractStable<CacheManagerEvent,CacheManagerListener> implements CacheManager{

	private static final Logger logger = LoggerFactory.getLogger(AbstractCacheManager.class);
	private static final String CACHE_CREATE_SUCCESS = "CACHE_CREATE_SUCCESS";

	private CacheKeyPolicy cacheKeyPolicy;
	private Map<String,Cache> caches = new ConcurrentHashMap<String,Cache>();

	public AbstractCacheManager() throws Exception{
		this.start();
	}

	@Override
	public Cache getCache(String cacheName){
		if(StringUtils.isEmpty(cacheName)){
			throw new IllegalArgumentException("Cache name must not be null");
		}
		Cache cache = this.caches.get(cacheName);
		if(null == cache){
			this.caches.put(cacheName,this.doCreateCache(cacheName));
			cache = this.caches.get(cacheName);
		}
		return cache;
	}

	@Override
	public Set<String> getCacheNames(){
		return Collections.unmodifiableSet(this.caches.keySet());
	}

	@Override
	public Map<String,Cache> getCaches(){
		return Collections.unmodifiableMap(this.caches);
	}

	@Override
	public void setCacheNames(String... cacheNames){
		if(null == cacheNames || cacheNames.length == 0){
			return;
		}
		for(int i = 0;i < cacheNames.length;i++){
			String cacheName = cacheNames[i];
			if(this.caches.containsKey(cacheName)){
				continue;
			}
			final Cache cache = this.doCreateCache(cacheName);
			this.caches.put(cacheName,cache);
		}
	}

	@Override
	public void setCacheKeyPolicy(CacheKeyPolicy cacheKeyPolicy){
		if(null == cacheKeyPolicy){
			return;
		}
		this.cacheKeyPolicy = cacheKeyPolicy;
	}

	@Override
	public CacheKeyPolicy getCacheKeyPolicy(){
		return this.cacheKeyPolicy;
	}

	@Override
	public void attachCacheCreateListener(CacheManagerListener listener){
		this.attachListener(CACHE_CREATE_SUCCESS,listener);
	}

	@Override
	public void detachCacheCreateListener(CacheManagerListener listener){
		this.detachListener(CACHE_CREATE_SUCCESS,listener);
	}

	@Override
	protected CacheManagerEvent createEvent(){
		return new CacheManagerEvent() {
			@Override
			public CacheManager getCacheManager(){
				return AbstractCacheManager.this;
			}

			@Override
			public Cache getCache(){
				return null;
			}
		};
	}

	@Override
	protected void doStart() throws Exception{
		this.cacheKeyPolicy = this.createCacheKeyPolicy();
		this.caches.clear();
	}

	@Override
	protected void doClose() throws Exception{
		if(null != this.caches && this.caches.size() > 0){
			for(Entry<String,Cache> entry : this.caches.entrySet()){
				try{
					entry.getValue().close();
					logger.debug("cache close success {}",entry.getValue().getCacheName());
				}catch(Exception e){
					logger.warn("cache close failed {}",e.getMessage(),e);
				}
			}
			this.caches.clear();
		}
	}

	protected Cache doCreateCache(String cacheName){
		final Cache cache = this.createCache(cacheName);
		this.fireEvent(CACHE_CREATE_SUCCESS,new EventableCallBack<CacheManagerEvent>() {
			@Override
			public CacheManagerEvent callback(final CacheManagerEvent event){
				return new CacheManagerEvent() {
					@Override
					public CacheManager getCacheManager(){
						return event.getCacheManager();
					}

					@Override
					public Cache getCache(){
						return cache;
					}
				};
			}
		});
		return cache;
	}

	protected abstract Cache createCache(String cacheName);

	protected abstract CacheKeyPolicy createCacheKeyPolicy();

}