package com.jcute.core.network;

import io.netty.channel.ChannelHandlerContext;

import com.jcute.core.network.net.NetServer;
import com.jcute.core.network.net.NetServerHandler;
import com.jcute.core.network.net.NetServerOptions;
import com.jcute.core.network.net.factory.NetServerHandlerFactoryForString;
import com.jcute.core.network.net.support.AbstractNetServerHandler;
import com.jcute.core.network.net.support.DefaultNetServerOptions;
import com.jcute.core.network.support.DefaultNetWorkAddress;
import com.jcute.core.network.support.DefaultNetWorkManager;

public class TestNetServer{

	public static void main(String[] args) throws Exception{

		NetWorkManager netWorkManager = new DefaultNetWorkManager();
		NetServerOptions netServerOptions = new DefaultNetServerOptions();
		netServerOptions.setNetWorkAddress(new DefaultNetWorkAddress(9080));
		NetServerHandlerFactoryForString serverHandlerFactoryForString = new NetServerHandlerFactoryForString(netServerOptions) {
			@Override
			public NetServerHandler createHandler(){
				return new AbstractNetServerHandler() {
					@Override
					public void onConnect(ChannelHandlerContext context) throws Exception{
						System.out.println(context.channel().remoteAddress());
					}
					@Override
					public void unConnect(ChannelHandlerContext context) throws Exception{
						System.out.println(context.channel().remoteAddress());
					}
					public void onException(ChannelHandlerContext context,Throwable cause) throws Exception{
						cause.printStackTrace();
					}
					@Override
					public void onMessage(ChannelHandlerContext context,Object message) throws Exception{
						System.out.println(message);
					}
				};
			}
		};
		netServerOptions.setNetServerHandlerFactory(serverHandlerFactoryForString);
		NetServer netServer = netWorkManager.createNetServer(netServerOptions);
		netServer.start();

	}

}
