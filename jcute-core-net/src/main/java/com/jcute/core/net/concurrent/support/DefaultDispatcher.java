package com.jcute.core.net.concurrent.support;

import java.util.LinkedList;
import java.util.Queue;

import com.jcute.core.net.concurrent.Dispatcher;
import com.jcute.core.net.session.Session;

public class DefaultDispatcher implements Dispatcher{

	private final ThreadLocal<Queue<Runnable>> threadLocal = new ThreadLocal<Queue<Runnable>>();

	@Override
	public void block(){

	}

	@Override
	public void dispatch(Session session,Runnable runner){
		Queue<Runnable> queue = this.getQueue();
		queue.add(runner);
		if(queue.size() == 1){
			runner.run();
			queue.poll();
			for(Runnable task = null;(task = (Runnable)queue.peek()) != null;queue.poll()){
				task.run();
			}
		}
	}

	private Queue<Runnable> getQueue(){
		Queue<Runnable> queue = this.threadLocal.get();
		if(null == queue){
			queue = new LinkedList<Runnable>();
			this.threadLocal.set(queue);
		}
		return queue;
	}

}