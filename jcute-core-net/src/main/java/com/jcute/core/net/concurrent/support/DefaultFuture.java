package com.jcute.core.net.concurrent.support;

import java.util.LinkedList;
import java.util.Queue;

import com.jcute.core.net.concurrent.Dispatcher;
import com.jcute.core.net.concurrent.DispatcherManager;
import com.jcute.core.net.concurrent.Future;
import com.jcute.core.net.concurrent.FutureListener;
import com.jcute.core.net.session.Session;
import com.jcute.core.net.util.ElapsedTime;

public class DefaultFuture implements Future{

	private final Session session;
	private final Dispatcher dispatcher;

	private volatile Queue<FutureListener> listeners;
	private volatile boolean completed = false;
	private volatile boolean successed = false;

	public DefaultFuture(Session session,DispatcherManager dispatcherManager,boolean successed){
		this.session = session;
		this.dispatcher = dispatcherManager.getDispatcher();
		this.completed = true;
		this.successed = successed;
	}

	public DefaultFuture(Session session,DispatcherManager dispatcherManager){
		this.session = session;
		this.dispatcher = dispatcherManager.getDispatcher();
	}

	@Override
	public Session getSession(){
		return this.session;
	}

	@Override
	public boolean complete(){
		if(!this.completed){
			synchronized(this){
				while(!this.completed){
					this.dispatcher.block();
					try{
						this.wait();
					}catch(InterruptedException e){}
				}
			}
		}
		return this.successed;
	}

	@Override
	public boolean complete(int timeout){
		if(timeout < 0){
			throw new IllegalArgumentException();
		}
		if(!this.completed){
			ElapsedTime startTime = new ElapsedTime();
			synchronized(this){
				while(!this.completed){
					long waitTime = timeout - startTime.getElapsedTime();
					if(waitTime <= 0){
						break;
					}
					dispatcher.block();
					try{
						this.wait(waitTime);
					}catch(InterruptedException e){}
				}
			}
		}
		return this.completed;
	}

	@Override
	public boolean isCompleted(){
		return this.completed;
	}

	@Override
	public boolean isSuccess(){
		return this.successed;
	}

	@Override
	public boolean isFailure(){
		return this.successed == false;
	}

	@Override
	public synchronized void setSuccess(){
		this.setSuccessed(true);
	}

	@Override
	public synchronized void setFailure(){
		this.setSuccessed(false);
	}

	@Override
	public void attachListener(FutureListener listener){
		if(null == listener){
			return;
		}
		if(this.completed){
			this.dispatchFutureCompleted(listener);
		}else{
			synchronized(this){
				if(this.completed){
					this.dispatchFutureCompleted(listener);
				}else{
					if(null == this.listeners){
						this.listeners = new LinkedList<FutureListener>();
					}
					this.listeners.add(listener);
				}
			}
		}
	}

	@Override
	public void detachListener(FutureListener listener){
		if(!this.completed){
			synchronized(this){
				if(!this.completed && null != this.listeners){
					this.listeners.remove(listener);
				}
			}
		}
	}

	protected void caughtException(Throwable cause){
		session.getSessionFilterChain(false).onExceptionCaught(cause);
	}

	protected void dispatchFutureCompleted(final FutureListener listener){
		this.dispatcher.dispatch(this.session,new Runnable() {
			private void futureCompleted(FutureListener lis){
				try{
					lis.complete(DefaultFuture.this);
				}catch(Exception e){
					caughtException(e);
				}
			}

			@Override
			public void run(){
				if(null != listener){
					futureCompleted(listener);
				}else if(null != listeners){
					for(FutureListener listener = null;(listener = (FutureListener)listeners.poll()) != null;){
						futureCompleted(listener);
					}
				}
			}
		});
	}

	protected synchronized void setSuccessed(boolean successed){
		if(this.completed){
			throw new IllegalStateException("can't change the state of a completed future");
		}
		this.completed = true;
		this.successed = successed;
		this.notifyAll();
		this.dispatchFutureCompleted(null);
	}

}