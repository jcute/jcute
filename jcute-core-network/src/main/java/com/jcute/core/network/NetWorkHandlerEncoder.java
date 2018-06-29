package com.jcute.core.network;

import io.netty.channel.ChannelHandlerContext;

public interface NetWorkHandlerEncoder{

	public Object encode(ChannelHandlerContext context,Object source) throws Exception;

}