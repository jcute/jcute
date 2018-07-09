package com.jcute.core.net.toolkit.support;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.net.toolkit.Configurable;

public abstract class AbstractConfigurable implements Configurable{

	private Map<Object,Object> properties = new ConcurrentHashMap<Object,Object>();

	@Override
	public Map<Object,Object> getProperties(){
		return Collections.unmodifiableMap(this.properties);
	}

	@Override
	public Object getProperty(Object key){
		return this.properties.get(key);
	}

	@Override
	public Object detachProperty(Object key){
		Object result = this.getProperty(key);
		this.properties.remove(key);
		return result;
	}

	@Override
	public Object attachProperty(Object key,Object value){
		this.properties.put(key,value);
		return value;
	}

}