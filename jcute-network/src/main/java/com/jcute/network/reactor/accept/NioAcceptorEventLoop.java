package com.jcute.network.reactor.accept;

import com.jcute.network.toolkit.NetAddress;
import com.jcute.network.toolkit.looper.EventLoop;

public interface NioAcceptorEventLoop extends EventLoop{
	
	@Override
	public NioAcceptorEventLoopGroup getEventLoopGroup();
	
	public NioAcceptorEventLoopChain getHandlerChain();
	
	public NetAddress getBindAddress();
	
}