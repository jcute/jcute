package com.jcute.core.net.loop.support;

import com.jcute.core.net.loop.EventLoop;
import com.jcute.core.net.loop.EventLoopGroup;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public abstract class AbstractEventLoop implements EventLoop{

	private static final Logger logger = LoggerFactory.getLogger(AbstractEventLoop.class);

	private Thread monitorThread;
	private EventLoopGroup eventLoopGroup;
	private Object locker = new Object();

	private volatile boolean running = false;
	private volatile boolean stopped = false;

	public AbstractEventLoop(EventLoopGroup eventLoopGroup){
		if(null == eventLoopGroup){
			throw new IllegalArgumentException("event loop group must not be null");
		}
		this.eventLoopGroup = eventLoopGroup;
	}

	@Override
	public EventLoopGroup getEventLoopGroup(){
		return this.eventLoopGroup;
	}

	@Override
	public Thread getMonitorThread(){
		return this.monitorThread;
	}

	@Override
	public boolean inEventLoop(){
		return this.inEventLoop(Thread.currentThread());
	}

	@Override
	public boolean inEventLoop(Thread thread){
		return this.getMonitorThread() == thread;
	}

	@Override
	public void looper(){
		for(;;){
			if(!this.running){
				this.stopped = true;
				return;
			}
			try{
				this.doLooper();
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
	}

	@Override
	public void wakeup(){
	}

	@Override
	public boolean isRunning(){
		return this.running;
	}

	@Override
	public boolean isStopped(){
		return this.stopped;
	}

	@Override
	public void start(String threadName) throws Exception{
		synchronized(this.locker){
			if(this.running){
				return;
			}
			this.running = true;
			this.stopped = false;
			this.monitorThread = new Thread(new Runnable() {
				@Override
				public void run(){
					looper();
				}
			},threadName);
			this.doStart();
			this.monitorThread.start();
		}
	}

	@Override
	public void close() throws Exception{
		synchronized(this.locker){
			if(!this.running){
				return;
			}
			this.running = false;
			try{
				this.wakeup();
			}catch(Throwable t){
				logger.error(t.getMessage(),t);
			}
			for(;!this.isStopped();){
				try{
					Thread.sleep(4);
				}catch(Exception e){}
			}
			try{
				this.doClose();
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
	}

	protected void doStart() throws Exception{
		logger.debug("event loop started");
	}

	protected void doClose() throws Exception{
		logger.debug("event loop stopped");
	}

	protected abstract void doLooper() throws Exception;

}