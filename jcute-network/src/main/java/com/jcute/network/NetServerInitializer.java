package com.jcute.network;

import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.connect.ConnectionHandler;

public interface NetServerInitializer{
	
	public PacketEncoder createPacketEncoder();
	
	public PacketDecoder createPacketDecoder();
	
	public ConnectionHandler createHandler();
	
}