package com.jcute.core.network.net;

import com.jcute.core.network.NetWorkHandlerFactory;

public interface NetClientHandlerFactory extends NetWorkHandlerFactory<NetClientHandler,NetClientEncoder,NetClientDecoder>{
	
	public NetClientOptions getNetClientOptions();
	
}