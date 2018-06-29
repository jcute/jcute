package com.jcute.core.network.net.support;

import io.netty.channel.ChannelHandlerContext;

import com.jcute.core.network.net.NetServerHandler;

public abstract class AbstractNetServerHandler implements NetServerHandler{

	@Override
	public void onConnect(ChannelHandlerContext context) throws Exception{
		
	}

	@Override
	public void unConnect(ChannelHandlerContext context) throws Exception{
		
	}

	@Override
	public void onException(ChannelHandlerContext context,Throwable cause) throws Exception{
		
	}

	@Override
	public void onMessage(ChannelHandlerContext context,Object message) throws Exception{
		
	}
	
}