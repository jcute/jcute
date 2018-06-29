package com.jcute.core.network;

import java.net.InetSocketAddress;

public interface NetWorkAddress{
	
	public String getHost();
	
	public int getPort();
	
	public InetSocketAddress toSocketAddress();
	
}