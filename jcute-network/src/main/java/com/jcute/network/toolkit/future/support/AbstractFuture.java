package com.jcute.network.toolkit.future.support;

import java.util.LinkedList;
import java.util.Queue;

import com.jcute.network.toolkit.ElapsedTime;
import com.jcute.network.toolkit.future.Future;
import com.jcute.network.toolkit.future.FutureListener;

public abstract class AbstractFuture<T> implements Future<T>{

	protected T target;

	private volatile Queue<FutureListener<T>> listeners;
	private volatile boolean completed = false;
	private volatile boolean successed = false;

	protected AbstractFuture(T target){
		this.target = target;
	}

	@Override
	public T getTarget(){
		return this.target;
	}

	@Override
	public boolean waitComplete(){
		if(!this.completed){
			synchronized(this){
				while(!this.completed){
					try{
						this.wait();
					}catch(InterruptedException e){}
				}
			}
		}
		return this.successed;
	}

	@Override
	public boolean waitComplete(int timeout){
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
					try{
						this.wait();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}
		return this.completed;
	}

	@Override
	public boolean isComplete(){
		return this.completed;
	}

	@Override
	public boolean isSuccess(){
		return this.successed == true;
	}

	@Override
	public boolean isFailure(){
		return this.successed == false;
	}

	@Override
	public void setSuccess(){
		this.setSuccessed(true);
	}

	@Override
	public void setFailure(){
		this.setSuccessed(false);
	}

	@Override
	public void attachListener(FutureListener<T> listener){
		if(null != listener){
			if(this.completed){
				this.dispatchFutureCompleted(listener);
			}else{
				synchronized(this){
					if(this.completed){
						this.dispatchFutureCompleted(listener);
					}else{
						if(null == this.listeners){
							this.listeners = new LinkedList<FutureListener<T>>();
						}
						this.listeners.add(listener);
					}
				}
			}
		}
	}

	@Override
	public void detachListener(FutureListener<T> listener){
		if(!this.completed){
			synchronized(this){
				if(!this.completed && null != this.listeners){
					this.listeners.remove(listener);
				}
			}
		}
	}

	protected abstract void dispatchFutureCompleted(FutureListener<T> listener);

	protected abstract void dispatchFutureCompleted(Queue<FutureListener<T>> listeners);

	private synchronized void setSuccessed(boolean successed){
		if(this.completed){
			throw new IllegalStateException("can not change the state of a completed future");
		}
		this.completed = true;
		this.successed = successed;
		this.notifyAll();
	}

}