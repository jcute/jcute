package com.jcute.network.reactor.accept;

import com.jcute.network.toolkit.looper.EventLoopGroup;

public interface NioAcceptorEventLoopGroup extends EventLoopGroup{
	
	@Override
	public NioAcceptorEventLoop getEventLoop(int index);
	
	@Override
	public NioAcceptorEventLoop getNextEventLoop();
	
}