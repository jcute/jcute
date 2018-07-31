package com.jcute.network.echo;

import com.jcute.network.NetServer;
import com.jcute.network.NetServerInitializer;
import com.jcute.network.packet.Packet;
import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.connect.ConnectionHandler;
import com.jcute.network.support.DefaultNetServer;
import com.jcute.network.toolkit.NetAddress;

public class TestEchoServer implements ConnectionHandler{

	public static void main(String[] args) throws Exception{
		NetServer netServer = new DefaultNetServer(NetAddress.create(9080),new NetServerInitializer() {
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
				return new TestEchoServer();
			}
		});
		netServer.start();

		System.in.read();

		netServer.close();

	}

	@Override
	public void onConnect(Connection connection){
		System.out.println("客户端上线:" + connection.getChannel());
	}

	@Override
	public void unConnect(Connection connection){
		System.out.println("客户端下线:" + connection.getChannel());
	}

	@Override
	public void OnMessage(Connection connection,Packet packet){
		System.out.println("收到客户端消息:" + connection.getChannel() + " => " + packet);
	}

	@Override
	public void onException(Connection connection,Throwable e){
		System.out.println("异常:" + e.getMessage());
	}

}