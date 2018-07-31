package com.jcute.network.reactor.dispacth;

import java.nio.channels.Selector;

import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.toolkit.looper.EventLoop;

public interface NioDispatcherEventLoop extends EventLoop{
	
	@Override
	public NioDispatcherEventLoopGroup getEventLoopGroup();
	
	public NioDispatcherEventLoopChain getHandlerChain();
	
	public PacketEncoder getPacketEncoder();
	
	public PacketDecoder getPacketDecoder();
	
	public void postRegist(Connection connection);
	
	public void postConnect(Connection connection,Selector selector);
	
}