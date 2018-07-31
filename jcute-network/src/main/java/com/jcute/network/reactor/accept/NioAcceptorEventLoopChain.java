package com.jcute.network.reactor.accept;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.jcute.network.toolkit.handler.HandlerChain;

public interface NioAcceptorEventLoopChain extends HandlerChain<NioAcceptorEventLoopHandler,NioAcceptorEventLoopContext,NioAcceptorEventLoopChain>{

	public void fireOnAccept(ServerSocketChannel serverChannel);

	public void fireOnSelect(ServerSocketChannel serverChannel,SocketChannel clientChannel);
	
	public NioAcceptorEventLoop getNioAcceptorEventLoop();

}