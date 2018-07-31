package com.jcute.network.echo;

import com.jcute.network.NetClient;
import com.jcute.network.NetClientInitializer;
import com.jcute.network.packet.Packet;
import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.connect.ConnectionHandler;
import com.jcute.network.support.DefaultNetClient;
import com.jcute.network.toolkit.NetAddress;

public class TestEchoClient implements ConnectionHandler{

	public static void main(String[] args) throws Exception{
		NetClient netClient = new DefaultNetClient(NetAddress.create("localhost",9080),new NetClientInitializer() {
			@Override
			public PacketEncoder createPacketEncoder(){
				return new MessagePacketEncoder();
			}

			@Override
			public PacketDecoder createPacketDecoder(){
				return new MessagePacketDecoder();
			}

			@Override
			public ConnectionHandler createHandler(){
				return new TestEchoClient();
			}
		});

		netClient.start();
		
		Thread.sleep(1000);
		
		MessagePacket packet = new MessagePacket("hello");
//		System.out.println(netClient.getBindAddress());
//		System.out.println(netClient.getConnectAddress());
		netClient.getConnection().writeAndFlush(packet);
		
	}

	@Override
	public void onConnect(Connection connection){
		System.out.println("连接服务端成功:" + connection.getChannel());
	}

	@Override
	public void unConnect(Connection connection){
		System.out.println("与服务端断开连接:" + connection.getChannel());
	}

	@Override
	public void OnMessage(Connection connection,Packet packet){
		System.out.println("收到服务端消息:" + connection.getChannel() + " => " + packet);
	}

	@Override
	public void onException(Connection connection,Throwable e){
		System.out.println("异常:" + e.getMessage());
	}

}