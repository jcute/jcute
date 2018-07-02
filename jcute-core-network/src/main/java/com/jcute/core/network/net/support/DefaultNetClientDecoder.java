package com.jcute.core.network.net.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import com.jcute.core.network.net.NetClientDecoder;

public class DefaultNetClientDecoder extends MessageToMessageDecoder<ByteBuf>{

	private NetClientDecoder netClientDecoder;

	public DefaultNetClientDecoder(NetClientDecoder netClientDecoder){
		if(null == netClientDecoder){
			throw new IllegalArgumentException("net client decoder must not be null");
		}
		this.netClientDecoder = netClientDecoder;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx,ByteBuf msg,List<Object> out) throws Exception{
		Object result = this.netClientDecoder.decode(ctx,msg);
		if(null != result){
			out.add(result);
		}
	}

}