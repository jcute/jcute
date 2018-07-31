package com.jcute.network.reactor.dispacth;

import com.jcute.network.toolkit.looper.EventLoopGroup;

public interface NioDispatcherEventLoopGroup extends EventLoopGroup{
	
	@Override
	public NioDispatcherEventLoop getEventLoop(int index);
	
	@Override
	public NioDispatcherEventLoop getNextEventLoop();
	
}