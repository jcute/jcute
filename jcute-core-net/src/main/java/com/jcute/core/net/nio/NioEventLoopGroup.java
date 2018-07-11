package com.jcute.core.net.nio;

import com.jcute.core.net.loop.EventLoopGroup;

public interface NioEventLoopGroup extends EventLoopGroup{

	@Override
	public NioEventLoop getEventLoop(int index);

	@Override
	public NioEventLoop getNextEventLoop();

}