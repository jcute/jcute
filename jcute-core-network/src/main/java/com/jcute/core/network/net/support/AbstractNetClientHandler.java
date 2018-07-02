package com.jcute.core.network.net.support;

import com.jcute.core.network.net.NetClientHandler;

import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractNetClientHandler implements NetClientHandler{

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
