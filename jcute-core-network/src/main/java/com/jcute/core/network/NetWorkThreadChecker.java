package com.jcute.core.network;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

import com.jcute.core.exception.JCuteException;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public class NetWorkThreadChecker{

	private static final Logger logger = LoggerFactory.getLogger(NetWorkThreadChecker.class);

	private static final Object monitor = new Object();

	private final Map<NetWorkThread,Object> threads = new WeakHashMap<NetWorkThread,Object>();
	private final Timer timer;

	public NetWorkThreadChecker(long interval,final long warningExceptionTime){
		this.timer = new Timer("network-thread-checker",true);
		this.timer.schedule(new TimerTask() {
			@Override
			public void run(){
				synchronized(NetWorkThreadChecker.this){
					long now = System.nanoTime();
					for(NetWorkThread thread : threads.keySet()){
						long start = thread.getStartTime();
						long durre = now - start;
						long limit = thread.getMaxExecuteTime();
						if(start != 0 && durre > limit){
							String message = String.format("thread %s has been blocked for %s ms,time limit is %s",thread.getName(),durre / 1000000,limit / 1000000);
							if(durre <= warningExceptionTime){
								logger.warn(message);
							}else{
								JCuteException exception = new JCuteException("thread blocked");
								exception.setStackTrace(thread.getStackTrace());
								logger.warn(message,exception);
							}
						}
					}
				}
			}
		},interval,interval);
	}

	public synchronized void attachThread(NetWorkThread thread){
		this.threads.put(thread,monitor);
	}

	public synchronized void close(){
		this.timer.cancel();
	}

}