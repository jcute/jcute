package com.jcute.network.reactor.dispacth;

import java.nio.channels.Selector;

import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.toolkit.handler.Handler;

public interface NioDispatcherEventLoopHandler extends Handler{

	public boolean onConnect(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector);
	
	public boolean onRegist(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector);

	public boolean onReader(NioDispatcherEventLoop eventLoop,Connection connection);

	public boolean onWriter(NioDispatcherEventLoop eventLoop,Connection connection);

	public boolean onCaught(NioDispatcherEventLoop eventLoop,Connection connection,Throwable cause);

}