package com.jcute.core.network.net.support;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.jcute.core.network.net.NetClientEncoder;

public class DefaultNetClientEncoder extends MessageToMessageEncoder<Object>{
	
	private NetClientEncoder netClientEncoder;
	
	public DefaultNetClientEncoder(NetClientEncoder netClientEncoder){
		if(null == netClientEncoder){
			throw new IllegalArgumentException("net client encoder must not be null");
		}
		this.netClientEncoder = netClientEncoder;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx,Object msg,List<Object> out) throws Exception{
		Object result = this.netClientEncoder.encode(ctx,msg);
		if(null != result){
			out.add(result);
		}
	}
	
}