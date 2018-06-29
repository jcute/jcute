package com.jcute.core.network.net.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import com.jcute.core.network.net.NetServerDecoder;

public class DefaultNetServerDecoder extends MessageToMessageDecoder<ByteBuf>{

	private NetServerDecoder netServerDecoder;

	public DefaultNetServerDecoder(NetServerDecoder netServerDecoder){
		if(null == netServerDecoder){
			throw new IllegalArgumentException("net server decoder must not be null");
		}
		this.netServerDecoder = netServerDecoder;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx,ByteBuf msg,List<Object> out) throws Exception{
		Object result = this.netServerDecoder.decode(ctx,msg);
		if(null != result){
			out.add(result);
		}
	}

}