package com.jcute.plugin.cache.support;

import com.jcute.plugin.cache.CacheValueWrapper;

public class DefaultCacheValueWrapper implements CacheValueWrapper{

	private Object value;

	public DefaultCacheValueWrapper(Object value){
		this.value = value;
	}

	@Override
	public Object getValue(){
		return this.value;
	}

	@Override
	public boolean isNullValue(){
		return this.value == null;
	}

}