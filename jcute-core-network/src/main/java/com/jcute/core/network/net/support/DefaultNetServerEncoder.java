package com.jcute.core.network.net.support;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.jcute.core.network.net.NetServerEncoder;

public class DefaultNetServerEncoder extends MessageToMessageEncoder<Object>{
	
	private NetServerEncoder netServerEncoder;
	
	public DefaultNetServerEncoder(NetServerEncoder netServerEncoder){
		if(null == netServerEncoder){
			throw new IllegalArgumentException("net server encoder must not be null");
		}
		this.netServerEncoder = netServerEncoder;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx,Object msg,List<Object> out) throws Exception{
		Object result = this.netServerEncoder.encode(ctx,msg);
		if(null != result){
			out.add(result);
		}
	}
	
}