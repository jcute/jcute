package com.jcute.core.network.net.support;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.net.NetServerHandlerFactory;
import com.jcute.core.network.net.NetServerOptions;
import com.jcute.core.network.net.factory.NetServerHandlerFactoryForString;

public class DefaultNetServerOptions extends AbstractNetServerOptions{

	public DefaultNetServerOptions(NetServerHandlerFactory netServerHandlerFactory){
		super(netServerHandlerFactory);
	}

	public DefaultNetServerOptions(){
		super(null);
	}

	@Override
	protected NetWorkAddress doCreateDefaultNetWorkAddress(){
		return NetWorkAddress.create();
	}

	@Override
	protected NetServerHandlerFactory doCreateDefaultNetServerHandlerFactory(NetServerOptions netServerOptions){
		return new NetServerHandlerFactoryForString(netServerOptions);
	}

}