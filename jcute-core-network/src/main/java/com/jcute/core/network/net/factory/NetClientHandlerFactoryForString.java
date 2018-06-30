package com.jcute.core.network.net.factory;

import java.nio.CharBuffer;

import com.jcute.core.network.net.NetClientDecoder;
import com.jcute.core.network.net.NetClientEncoder;
import com.jcute.core.network.net.NetClientHandler;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.network.net.support.AbstractNetClientHandler;
import com.jcute.core.network.net.support.AbstractNetClientHandlerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;

public class NetClientHandlerFactoryForString extends AbstractNetClientHandlerFactory{

	public NetClientHandlerFactoryForString(NetClientOptions netClientOptions){
		super(netClientOptions);
	}

	@Override
	public NetClientHandler createHandler(){
		return new AbstractNetClientHandler(){
		};
	}

	@Override
	public NetClientEncoder createEncoder(){
		return new NetClientEncoder(){
			@Override
			public Object encode(ChannelHandlerContext context,Object source) throws Exception{
				if(null == source){
					return null;
				}
				return ByteBufUtil.encodeString(context.alloc(),CharBuffer.wrap(source.toString()),getNetClientOptions().getCharset());
			}
		};
	}

	@Override
	public NetClientDecoder createDecoder(){
		return new NetClientDecoder(){
			@Override
			public Object decode(ChannelHandlerContext context,ByteBuf source) throws Exception{
				return source.toString(getNetClientOptions().getCharset());
			}
		};
	}

}