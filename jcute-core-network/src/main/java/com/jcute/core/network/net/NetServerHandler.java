package com.jcute.core.network.net;

import io.netty.channel.ChannelHandlerContext;

import com.jcute.core.network.NetWorkHandler;

public interface NetServerHandler extends NetWorkHandler{

	public void onConnect(ChannelHandlerContext context) throws Exception;

	public void unConnect(ChannelHandlerContext context) throws Exception;

	public void onException(ChannelHandlerContext context,Throwable cause) throws Exception;

	public void onMessage(ChannelHandlerContext context,Object message) throws Exception;

}