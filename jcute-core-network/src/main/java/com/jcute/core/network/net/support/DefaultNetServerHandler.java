package com.jcute.core.network.net.support;

import com.jcute.core.network.net.NetServerHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DefaultNetServerHandler extends ChannelInboundHandlerAdapter{

	private NetServerHandler netServerHandler;

	public DefaultNetServerHandler(NetServerHandler netServerHandler){
		this.netServerHandler = netServerHandler;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
		if(null != this.netServerHandler){
			this.netServerHandler.onMessage(ctx,msg);
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception{
		if(null != this.netServerHandler){
			this.netServerHandler.onConnect(ctx);
		}
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
		if(null != this.netServerHandler){
			this.netServerHandler.unConnect(ctx);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
		if(null != this.netServerHandler){
			this.netServerHandler.onException(ctx,cause);
		}
		ctx.close();
	}

}