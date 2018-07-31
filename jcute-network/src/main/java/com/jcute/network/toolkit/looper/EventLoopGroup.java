package com.jcute.network.toolkit.looper;

import com.jcute.network.toolkit.execute.Executable;

public interface EventLoopGroup extends Executable{

	public int getEventLoopSize();
	
	public EventLoop getEventLoop(int index);
	
	public EventLoop getNextEventLoop();
	
}