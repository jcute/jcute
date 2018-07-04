package com.jcute.core.network.net.support;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.net.NetClientHandlerFactory;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.network.net.factory.NetClientHandlerFactoryForString;

public class DefaultNetClientOptions extends AbstractNetClientOptions{

	public DefaultNetClientOptions(NetClientHandlerFactory netClientHandlerFactory){
		super(netClientHandlerFactory);
	}

	public DefaultNetClientOptions(){
		super(null);
	}

	@Override
	protected NetWorkAddress doCreateDefaultNetWorkAddress(){
		return NetWorkAddress.create();
	}
	
	@Override
	protected NetClientHandlerFactory doCreateDefaultNetClientHandlerFactory(NetClientOptions options){
		return new NetClientHandlerFactoryForString(options);
	}

}