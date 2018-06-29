package com.jcute.core.network.net.support;

import com.jcute.core.network.net.NetServerHandlerFactory;
import com.jcute.core.network.net.NetServerOptions;

public abstract class AbstractNetServerHandlerFactory implements NetServerHandlerFactory{

	protected NetServerOptions netServerOptions;

	public AbstractNetServerHandlerFactory(NetServerOptions netServerOptions){
		if(null == netServerOptions){
			throw new IllegalArgumentException("net server options must not be null");
		}
		this.netServerOptions = netServerOptions;
	}

	@Override
	public NetServerOptions getNetServerOptions(){
		return this.netServerOptions;
	}

}