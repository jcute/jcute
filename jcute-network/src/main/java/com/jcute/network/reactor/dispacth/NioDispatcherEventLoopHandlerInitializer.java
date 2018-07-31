package com.jcute.network.reactor.dispacth;

import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;


public interface NioDispatcherEventLoopHandlerInitializer{
	
	public void initHandler(NioDispatcherEventLoopChain chain);
	
	public PacketEncoder getPacketEncoder();
	
	public PacketDecoder getPacketDecoder();
	
}