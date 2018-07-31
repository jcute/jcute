package com.jcute.network;

import java.nio.ByteBuffer;

import com.jcute.network.packet.Packet;
import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.connect.ConnectionHandler;
import com.jcute.network.support.DefaultNetServer;
import com.jcute.network.toolkit.NetAddress;

public class TestNetServer implements ConnectionHandler, PacketEncoder, PacketDecoder{

	public static void main(String[] args) throws Exception{
		final TestNetServer server = new TestNetServer();
		NetServer netServer = new DefaultNetServer(NetAddress.create(9080),new NetServerInitializer() {
			@Override
			public PacketEncoder createPacketEncoder(){
				return server;
			}

			@Override
			public PacketDecoder createPacketDecoder(){
				return server;
			}

			@Override
			public ConnectionHandler createHandler(){
				return server;
			}
		});
		netServer.start();

		System.in.read();

		netServer.close();

	}

	@Override
	public void onConnect(Connection connection){
//		System.out.println("handler -> onConnect ");
	}

	@Override
	public void unConnect(Connection connection){
//		System.out.println("handler -> unConnect ");
	}

	@Override
	public void OnMessage(Connection connection,Packet packet){
		ByteArrayPacket data = (ByteArrayPacket)packet;
		System.out.println(new String(data.getData()));
		ByteArrayPacket msg = new ByteArrayPacket("HTTP/1.1 200 OK\nContent-Type:text/html\nContent-Length:5\n\nHello".getBytes());
		connection.write(msg);
		connection.flush();
	}

	@Override
	public void onException(Connection connection,Throwable e){
//		System.out.println("handler -> onException ");
		e.printStackTrace();
	}

	@Override
	public Packet decode(ByteBuffer buffer){
		if(buffer.hasRemaining()){
			return new ByteArrayPacket(buffer.array());
		}
		return null;
	}

	@Override
	public ByteBuffer encode(Packet packet){
		return ByteBuffer.wrap(((ByteArrayPacket)packet).getData());
	}

}