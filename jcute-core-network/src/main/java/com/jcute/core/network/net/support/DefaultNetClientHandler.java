package com.jcute.core.network.net.support;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.jcute.core.network.net.NetClientHandler;

public class DefaultNetClientHandler extends ChannelInboundHandlerAdapter{

	private NetClientHandler netClientHandler;

	public DefaultNetClientHandler(NetClientHandler netClientHandler){
		this.netClientHandler = netClientHandler;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
		if(null != this.netClientHandler){
			this.netClientHandler.onMessage(ctx,msg);
		}
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception{
		if(null != this.netClientHandler){
			this.netClientHandler.onConnect(ctx);
		}
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
		if(null != this.netClientHandler){
			this.netClientHandler.unConnect(ctx);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
		if(null != this.netClientHandler){
			this.netClientHandler.onException(ctx,cause);
		}
		ctx.close();
	}

}