package com.jcute.core.net.session.support;

import com.jcute.core.net.concurrent.Future;
import com.jcute.core.net.concurrent.support.DefaultFuture;
import com.jcute.core.net.message.Message;

public abstract class AbstractChannelSession extends AbstractSession{
	
	private volatile boolean running = false;
	private DefaultFuture startFuture;
	private DefaultFuture closeFuture;

	@Override
	public final synchronized Future start(){
		if(null != closeFuture && !closeFuture.isCompleted()){
			return new DefaultFuture(this,this.getDispatcherManager(),false);
		}
		this.closeFuture = null;
		if(null == this.startFuture){
			try{
				this.doStart();
			}catch(Exception e){
				this.dispatchException(e);
				return new DefaultFuture(this,this.getDispatcherManager(),false);
			}
			this.startFuture = new DefaultFuture(this,this.getDispatcherManager());
			//TODO
//			reactor.register(handler);
		}
		return this.startFuture;
	}

	@Override
	public Future close(){
		return null;
	}

	@Override
	protected Future send(Object data,Message message,int priority){
		return null;
	}
	
	@Override
	public boolean isRunning(){
		return this.running;
	}
	
	protected void doStart() throws Exception{

	}

	protected void doClose() throws Exception{

	}

}