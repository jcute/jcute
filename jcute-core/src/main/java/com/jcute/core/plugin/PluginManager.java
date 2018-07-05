package com.jcute.core.plugin;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import com.jcute.core.toolkit.cycle.Stable;

public interface PluginManager extends Stable<PluginManagerEvent,PluginManagerListener>{

	public Set<Plugin> getAllPlugins();

	public void attachPlugin(Plugin plugin);

	public void detachPlugin(Plugin plugin);
	
	public void attachPluginAddSuccessListener(PluginManagerListener listener);
	
	public void detachPluginAddSuccessListener(PluginManagerListener listener);
	
	public void attachPluginDelSuccessListener(PluginManagerListener listener);
	
	public void detachPluginDelSuccessListener(PluginManagerListener listener);
	
	public Map<Class<? extends Plugin>,Annotation> resolvePlugins(Class<?> beanType);
	
}