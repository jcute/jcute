package com.jcute.network.toolkit.looper;

import com.jcute.network.toolkit.execute.Executable;

public interface EventLoop extends Executable{

	public String getEventLoopName();

	public Thread getEventLoopThread();

	public EventLoopGroup getEventLoopGroup();

	public boolean inEventLoop();

	public boolean inEventLoop(Thread thread);

}