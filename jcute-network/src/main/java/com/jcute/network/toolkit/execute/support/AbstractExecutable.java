package com.jcute.network.toolkit.execute.support;

import com.jcute.network.toolkit.execute.Executable;

public abstract class AbstractExecutable implements Executable{

	private volatile boolean running = false;

	@Override
	public final synchronized void start() throws Exception{
		if(this.running){
			return;
		}
		this.running = true;
		this.doStart();
	}

	@Override
	public final synchronized void close() throws Exception{
		if(!this.running){
			return;
		}
		this.running = false;
		this.doClose();
	}

	@Override
	public boolean isRunning(){
		return this.running;
	}

	protected abstract void doStart() throws Exception;

	protected abstract void doClose() throws Exception;

}