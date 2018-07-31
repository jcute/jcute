package com.jcute.network;

import com.jcute.network.toolkit.NetAddress;
import com.jcute.network.toolkit.execute.Executable;

public interface NetServer extends Executable{
	
	public NetAddress getBindAddress();
	
	public NetServerInitializer getNetServerInitializer();
	
}