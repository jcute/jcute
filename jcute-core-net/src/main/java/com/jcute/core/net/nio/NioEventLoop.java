package com.jcute.core.net.nio;

import com.jcute.core.net.looper.EventLoop;

public interface NioEventLoop extends EventLoop{
	
	@Override
	public NioEventLoopGroup getEventLoopGroup();
	
}