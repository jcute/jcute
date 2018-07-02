package com.jcute.core.network.net;

import com.jcute.core.network.NetWorkHandler;

import io.netty.channel.ChannelHandlerContext;

public interface NetClientHandler extends NetWorkHandler{
	
	public void onConnect(ChannelHandlerContext context)throws Exception;
	
	public void unConnect(ChannelHandlerContext context)throws Exception;
	
	public void onException(ChannelHandlerContext context,Throwable cause)throws Exception;
	
	public void onMessage(ChannelHandlerContext context,Object message);
	
}