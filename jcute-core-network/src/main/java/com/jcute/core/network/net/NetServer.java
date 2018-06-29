package com.jcute.core.network.net;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.NetWorkManager;
import com.jcute.core.toolkit.cycle.Stable;

public interface NetServer extends Stable<NetServerEvent,NetServerListener>{
	
	public NetWorkManager getNetWorkManager();
	
	public NetServerOptions getNetServerOptions();
	
	public NetServerHandlerFactory getHandlerFactory();
	
	public NetWorkAddress getBindNetWorkAddress();
	
}