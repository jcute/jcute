package com.jcute.core.net.concurrent;

public interface EventLoop{

	public EventLoopGroup getEventLoopGroup();

	public Thread getMonitor();

	public boolean inEventLoop();

	public boolean inEventLoop(Thread thread);

	public boolean isRunning();

	public void loop();

	public void wakeup();

	public void start() throws Exception;

	public void close() throws Exception;

}