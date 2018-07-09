package com.jcute.core.net.toolkit;

import java.util.Map;

public interface Configurable{
	
	public Map<Object,Object> getProperties();
	
	public Object getProperty(Object key);
	
	public Object detachProperty(Object key);
	
	public Object attachProperty(Object key,Object value);
	
}