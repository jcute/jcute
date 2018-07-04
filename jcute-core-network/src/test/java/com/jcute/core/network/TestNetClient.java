package com.jcute.core.network;

import io.netty.channel.ChannelHandlerContext;

import com.jcute.core.network.net.NetClient;
import com.jcute.core.network.net.NetClientHandler;
import com.jcute.core.network.net.NetClientHandlerFactory;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.network.net.factory.NetClientHandlerFactoryForString;
import com.jcute.core.network.net.support.DefaultNetClientOptions;
import com.jcute.core.network.support.DefaultNetWorkManager;

public class TestNetClient{
	
	public static void main(String[] args) throws Exception{
		
		NetWorkManager netWorkManager = new DefaultNetWorkManager();
		NetClientOptions netClientOptions = new DefaultNetClientOptions();
		netClientOptions.setNetWorkAddress(NetWorkAddress.create(9080));
		NetClientHandlerFactory netClientHandlerFactory = new NetClientHandlerFactoryForString(netClientOptions){
			@Override
			public NetClientHandler createHandler(){
				return new NetClientHandler() {
					@Override
					public void onConnect(ChannelHandlerContext context) throws Exception{
						System.out.println("on connect " + context.channel());
					}
					@Override
					public void unConnect(ChannelHandlerContext context) throws Exception{
						System.out.println("un connect "+context.channel());
					}
					public void onException(ChannelHandlerContext context,Throwable cause) throws Exception{
						cause.printStackTrace();
					}
					@Override
					public void onMessage(ChannelHandlerContext context,Object message) throws Exception{
						System.out.println("on message" +message);
					}
				};
			}
		};
		netClientOptions.setHandlerFactory(netClientHandlerFactory);
		NetClient netClient = netWorkManager.createNetClient(netClientOptions);
		netClient.start();
		System.out.println(netClient.getBindNetWorkAddress());
		System.out.println(netWorkManager.getNetClients());
		Thread.sleep(1000);
		System.out.println(netWorkManager.getNetClients());
		
		netClient.close();
		netWorkManager.close();
		
	}
	
}