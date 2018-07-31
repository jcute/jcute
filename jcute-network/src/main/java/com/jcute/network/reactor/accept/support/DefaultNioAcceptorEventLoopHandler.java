package com.jcute.network.reactor.accept.support;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.jcute.network.reactor.accept.NioAcceptorEventLoop;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopHandler;

public class DefaultNioAcceptorEventLoopHandler implements NioAcceptorEventLoopHandler{

	@Override
	public boolean onAccept(NioAcceptorEventLoop eventLoop,ServerSocketChannel serverChannel){
		return true;
	}

	@Override
	public boolean onSelect(NioAcceptorEventLoop eventLoop,ServerSocketChannel serverChannel,SocketChannel clientChannel){
		return true;
	}

}