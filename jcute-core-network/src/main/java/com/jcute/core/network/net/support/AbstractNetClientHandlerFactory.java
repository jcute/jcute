package com.jcute.core.network.net.support;

import com.jcute.core.network.net.NetClientHandlerFactory;
import com.jcute.core.network.net.NetClientOptions;

public abstract class AbstractNetClientHandlerFactory implements NetClientHandlerFactory{

	protected NetClientOptions netClientOptions;

	public AbstractNetClientHandlerFactory(NetClientOptions netClientOptions){
		if(null == netClientOptions){
			throw new IllegalArgumentException("net client options must not be null");
		}
		this.netClientOptions = netClientOptions;
	}

	@Override
	public NetClientOptions getNetClientOptions(){
		return this.netClientOptions;
	}

}