package com.jcute.plugin.cache.support;

import java.util.HashMap;
import java.util.Map;

import com.jcute.plugin.cache.CacheKeyPolicy;

public abstract class AbstractCacheKeyPolicy implements CacheKeyPolicy{
	
	@Override
	public Object getCacheKey(Map<String,Object> context,String expression){
		if(null == context){
			context = new HashMap<String,Object>();
		}
		return this.doGetCacheKey(context,expression);
	}
	
	protected abstract Object doGetCacheKey(Map<String,Object> context,String expression);
	
}