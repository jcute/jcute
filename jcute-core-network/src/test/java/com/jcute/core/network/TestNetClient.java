package com.jcute.core.network;

import io.netty.channel.ChannelHandlerContext;

import com.jcute.core.network.net.NetClient;
import com.jcute.core.network.net.NetClientHandler;
import com.jcute.core.network.net.NetClientHandlerFactory;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.network.net.factory.NetClientHandlerFactoryForString;
import com.jcute.core.network.net.support.DefaultNetClientOptions;
import com.jcute.core.network.support.DefaultNetWorkAddress;
import com.jcute.core.network.support.DefaultNetWorkManager;

public class TestNetClient{
	
	public static void main(String[] args) throws Exception{
		
		NetWorkManager netWorkManager = new DefaultNetWorkManager();
		NetClientOptions netClientOptions = new DefaultNetClientOptions();
		netClientOptions.setNetWorkAddress(new DefaultNetWorkAddress(9080));
		NetClientHandlerFactory netClientHandlerFactory = new NetClientHandlerFactoryForString(netClientOptions){
			@Override
			public NetClientHandler createHandler(){
				return new NetClientHandler() {
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
		netClientOptions.setHandlerFactory(netClientHandlerFactory);
		NetClient netClient = netWorkManager.createNetClient(netClientOptions);
		netClient.start();
		
		Thread.sleep(1000);
		
		netClient.close();
		netWorkManager.close();
		
	}
	
}