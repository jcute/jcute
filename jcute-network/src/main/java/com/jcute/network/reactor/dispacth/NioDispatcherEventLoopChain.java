package com.jcute.network.reactor.dispacth;

import java.nio.channels.Selector;

import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.toolkit.handler.HandlerChain;

public interface NioDispatcherEventLoopChain extends HandlerChain<NioDispatcherEventLoopHandler,NioDispatcherEventLoopContext,NioDispatcherEventLoopChain>{
	
	public NioDispatcherEventLoop getNioDispatcherEventLoop();
	
	public void fireOnConnect(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector);
	
	public void fireOnRegist(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector);

	public void fireOnReader(NioDispatcherEventLoop eventLoop,Connection connection);

	public void fireOnWriter(NioDispatcherEventLoop eventLoop,Connection connection);

	public void fireOnCaught(NioDispatcherEventLoop eventLoop,Connection connection,Throwable cause);
	
}