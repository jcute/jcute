package com.jcute.core.network;

import io.netty.util.concurrent.FastThreadLocalThread;

public class NetWorkThread extends FastThreadLocalThread{

	private final boolean worker;
	private final long maxExecuteTime;
	private long executeStart;
	private NetWorkThreadContext context;

	public NetWorkThread(Runnable runnable,String name,boolean worker,long maxExecuteTime){
		super(runnable,name);
		this.worker = worker;
		this.maxExecuteTime = maxExecuteTime;
	}

	public NetWorkThreadContext getContext(){
		return this.context;
	}

	public void setContext(NetWorkThreadContext context){
		this.context = context;
	}

	public final void executeStart(){
		this.executeStart = System.nanoTime();
	}

	public final void executeClose(){
		this.executeStart = 0;
	}

	public long getStartTime(){
		return this.executeStart;
	}

	public boolean isWorker(){
		return this.worker;
	}

	public long getMaxExecuteTime(){
		return this.maxExecuteTime;
	}

}