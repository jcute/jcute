package com.jcute.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface NetWorkHandlerDecoder{
	
	public Object decode(ChannelHandlerContext context,ByteBuf source)throws Exception;
	
}