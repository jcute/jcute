package com.jcute.network.toolkit.looper.support;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.network.toolkit.execute.support.AbstractExecutable;
import com.jcute.network.toolkit.looper.EventLoop;
import com.jcute.network.toolkit.looper.EventLoopGroup;

public abstract class AbstractEventLoop extends AbstractExecutable implements EventLoop{

	private static final Logger logger = LoggerFactory.getLogger(AbstractEventLoop.class);

	private String eventLoopName;
	private EventLoopGroup eventLoopGroup;
	private Thread eventLoopThread;

	protected AbstractEventLoop(EventLoopGroup eventLoopGroup,String eventLoopName){
		this.eventLoopGroup = eventLoopGroup;
		this.eventLoopName = eventLoopName;
	}

	@Override
	public String getEventLoopName(){
		return this.eventLoopName;
	}

	@Override
	public Thread getEventLoopThread(){
		return this.eventLoopThread;
	}

	@Override
	public EventLoopGroup getEventLoopGroup(){
		return this.eventLoopGroup;
	}

	@Override
	public boolean inEventLoop(){
		return this.inEventLoop(Thread.currentThread());
	}

	@Override
	public boolean inEventLoop(Thread thread){
		return thread == this.eventLoopThread;
	}

	@Override
	protected final void doStart() throws Exception{
		this.eventLoopThread = new Thread(new Runnable() {
			@Override
			public void run(){
				loop();
			}
		},this.eventLoopName);
		this.onStart();
		this.eventLoopThread.start();
	}

	@Override
	protected final void doClose() throws Exception{
		this.onClose();
		this.eventLoopGroup = null;
		this.eventLoopName = null;
		this.eventLoopThread = null;
	}

	private void loop(){
		for(;this.isRunning();){
			try{
				this.doLoop();
			}catch(Exception e){
				e.printStackTrace();
				logger.error("{} loop error {}",this.getClass().getSimpleName(),e.getMessage(),e);
			}
		}
	}

	protected abstract void doLoop() throws Exception;

	protected abstract void onStart() throws Exception;

	protected abstract void onClose() throws Exception;

}