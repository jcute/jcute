package com.jcute.network.reactor.dispacth.support;

import java.nio.channels.Selector;

import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopHandler;

public class DefaultNioDispatcherEventLoopHandler implements NioDispatcherEventLoopHandler{
	
	@Override
	public boolean onConnect(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector){
		return true;
	}
	
	@Override
	public boolean onRegist(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector){
		return true;
	}

	@Override
	public boolean onReader(NioDispatcherEventLoop eventLoop,Connection connection){
		return true;
	}

	@Override
	public boolean onWriter(NioDispatcherEventLoop eventLoop,Connection connection){
		return true;
	}

	@Override
	public boolean onCaught(NioDispatcherEventLoop eventLoop,Connection connection,Throwable cause){
		return true;
	}

}