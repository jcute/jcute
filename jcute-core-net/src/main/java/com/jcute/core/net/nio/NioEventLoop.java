package com.jcute.core.net.nio;

import com.jcute.core.net.loop.EventLoop;

public interface NioEventLoop extends EventLoop{
	
	@Override
	public NioEventLoopGroup getEventLoopGroup();
	
}