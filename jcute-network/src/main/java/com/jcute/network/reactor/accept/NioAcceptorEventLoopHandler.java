package com.jcute.network.reactor.accept;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.jcute.network.toolkit.handler.Handler;

public interface NioAcceptorEventLoopHandler extends Handler{
	
	public boolean onAccept(NioAcceptorEventLoop eventLoop,ServerSocketChannel serverChannel);
	
	public boolean onSelect(NioAcceptorEventLoop eventLoop,ServerSocketChannel serverChannel,SocketChannel clientChannel);
	
}