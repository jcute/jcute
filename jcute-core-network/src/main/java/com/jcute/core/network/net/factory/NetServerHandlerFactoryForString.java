package com.jcute.core.network.net.factory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;

import java.nio.CharBuffer;

import com.jcute.core.network.net.NetServerDecoder;
import com.jcute.core.network.net.NetServerEncoder;
import com.jcute.core.network.net.NetServerHandler;
import com.jcute.core.network.net.NetServerOptions;
import com.jcute.core.network.net.support.AbstractNetServerHandler;
import com.jcute.core.network.net.support.AbstractNetServerHandlerFactory;

public class NetServerHandlerFactoryForString extends AbstractNetServerHandlerFactory{

	public NetServerHandlerFactoryForString(NetServerOptions netServerOptions){
		super(netServerOptions);
	}

	@Override
	public NetServerHandler createHandler(){
		return new AbstractNetServerHandler() {};
	}

	@Override
	public NetServerEncoder createEncoder(){
		return new NetServerEncoder() {
			@Override
			public Object encode(ChannelHandlerContext context,Object source) throws Exception{
				if(null == source){
					return null;
				}
				return ByteBufUtil.encodeString(context.alloc(),CharBuffer.wrap(source.toString()),getNetServerOptions().getCharset());
			}
		};
	}

	@Override
	public NetServerDecoder createDecoder(){
		return new NetServerDecoder() {
			@Override
			public Object decode(ChannelHandlerContext context,ByteBuf source) throws Exception{
				return source.toString(getNetServerOptions().getCharset());
			}
		};
	}

}