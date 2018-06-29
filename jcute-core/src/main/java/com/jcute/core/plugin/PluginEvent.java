package com.jcute.core.plugin;

import com.jcute.core.toolkit.cycle.StableEvent;

public interface PluginEvent extends StableEvent{
	
	public Plugin getPlugin();
	
}