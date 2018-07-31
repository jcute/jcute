package com.jcute.network;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.jcute.network.reactor.accept.NioAcceptorEventLoop;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopChain;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopGroup;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopHandlerInitializer;
import com.jcute.network.reactor.accept.support.DefaultNioAcceptorEventLoopGroup;
import com.jcute.network.reactor.accept.support.DefaultNioAcceptorEventLoopHandler;
import com.jcute.network.toolkit.NetAddress;

public class TestNioAcceptor implements NioAcceptorEventLoopHandlerInitializer{

	public static void main(String[] args) throws Exception{

		NioAcceptorEventLoopGroup nioAcceptorEventLoopGroup = new DefaultNioAcceptorEventLoopGroup(new TestNioAcceptor(),NetAddress.create(9080));
		nioAcceptorEventLoopGroup.start();

		System.in.read();

		nioAcceptorEventLoopGroup.close();

	}

	@Override
	public void initHandler(NioAcceptorEventLoopChain chain){

		chain.attachLast("demo",new DefaultNioAcceptorEventLoopHandler() {
			@Override
			public boolean onAccept(NioAcceptorEventLoop eventLoop,ServerSocketChannel serverChannel){
				try{
					serverChannel.socket().setReuseAddress(true);
					serverChannel.socket().setSoTimeout(0);
				}catch(SocketException e){
					e.printStackTrace();
				}
				return true;
			}

			@Override
			public boolean onSelect(NioAcceptorEventLoop eventLoop,ServerSocketChannel serverChannel,SocketChannel clientChannel){
				try{
					clientChannel.write(ByteBuffer.wrap("HTTP/1.1 200 OK\nContent-Length:5\nContent-Type:text/html\n\nHello".getBytes()));
				}catch(IOException e){
					e.printStackTrace();
				}
				return true;
			}
		});

		System.out.println(chain.getNioAcceptorEventLoop().getEventLoopName());

	}

}