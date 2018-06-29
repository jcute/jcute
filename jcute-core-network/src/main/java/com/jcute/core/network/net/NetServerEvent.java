package com.jcute.core.network.net;

import com.jcute.core.network.NetWorkManager;
import com.jcute.core.toolkit.cycle.StableEvent;

public interface NetServerEvent extends StableEvent{
	
	public NetServer getNetServer();
	
	public NetWorkManager getNetWorkManager();
	
}