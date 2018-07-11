package com.jcute.core.net.looper;

public interface EventLoop{

	public EventLoopGroup getEventLoopGroup();

	public Thread getMonitorThread();

	public boolean inEventLoop();

	public boolean inEventLoop(Thread thread);

	public boolean isRunning();
	
	public boolean isStopped();
	
	public void looper();

	public void wakeup();

	public void start(String threadName) throws Exception;

	public void close() throws Exception;

}