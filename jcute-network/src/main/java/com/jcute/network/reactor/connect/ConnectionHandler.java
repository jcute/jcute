package com.jcute.network.reactor.connect;

import com.jcute.network.packet.Packet;

public interface ConnectionHandler{
	
	public void onConnect(Connection connection);
	
	public void unConnect(Connection connection);
	
	public void OnMessage(Connection connection,Packet packet);
	
	public void onException(Connection connection,Throwable e);
	
}