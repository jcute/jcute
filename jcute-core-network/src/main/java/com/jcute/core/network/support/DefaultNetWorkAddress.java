package com.jcute.core.network.support;

import java.net.InetSocketAddress;

public class DefaultNetWorkAddress extends AbstractNetWorkAddress{

	public DefaultNetWorkAddress(){
		super(null,0);
	}

	public DefaultNetWorkAddress(int port){
		super(null,port);
	}

	public DefaultNetWorkAddress(String host){
		super(host,0);
	}

	public DefaultNetWorkAddress(String host,int port){
		super(host,port);
	}
	
	public DefaultNetWorkAddress(InetSocketAddress address){
		super(address.getHostName(),address.getPort());
	}

}