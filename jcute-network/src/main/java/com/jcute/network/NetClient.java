package com.jcute.network;

import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.toolkit.NetAddress;
import com.jcute.network.toolkit.execute.Executable;

public interface NetClient extends Executable{
	
	public NetAddress getBindAddress();
	
	public NetAddress getConnectAddress();
	
	public NetClientInitializer getNetClientInitializer();
	
	public Connection getConnection();
	
}