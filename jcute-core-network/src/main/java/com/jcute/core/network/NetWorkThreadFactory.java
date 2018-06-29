package com.jcute.core.network;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NetWorkThreadFactory implements ThreadFactory{

	private static final Object monitor = new Object();
	private static Map<NetWorkThread,Object> threads = new WeakHashMap<NetWorkThread,Object>();

	private final String prefix;
	private final AtomicInteger counter = new AtomicInteger(0);
	private final NetWorkThreadChecker checker;
	private final boolean worker;
	private final long maxExecuteTime;

	public NetWorkThreadFactory(String prefix,NetWorkThreadChecker checker,boolean worker,long maxExecuteTime){
		this.prefix = prefix;
		this.checker = checker;
		this.worker = worker;
		this.maxExecuteTime = maxExecuteTime;
	}

	public static synchronized void clearThreadContext(NetWorkThreadContext context){
		for(NetWorkThread thread : threads.keySet()){
			if(thread.getContext() == context){
				thread.setContext(null);
			}
		}
	}

	private static synchronized void attachMapping(NetWorkThread thread){
		threads.put(thread,monitor);
	}

	@Override
	public Thread newThread(Runnable r){
		NetWorkThread thread = new NetWorkThread(r,String.format("%s-%d",this.prefix,this.counter.getAndIncrement()),this.worker,this.maxExecuteTime);
		if(null != this.checker){
			this.checker.attachThread(thread);
		}
		attachMapping(thread);
		thread.setDaemon(false);
		return thread;
	}

}