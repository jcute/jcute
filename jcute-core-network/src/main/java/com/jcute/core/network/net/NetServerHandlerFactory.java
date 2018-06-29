package com.jcute.core.network.net;

import com.jcute.core.network.NetWorkHandlerFactory;

public interface NetServerHandlerFactory extends NetWorkHandlerFactory<NetServerHandler,NetServerEncoder,NetServerDecoder>{
	
	public NetServerOptions getNetServerOptions();
	
}