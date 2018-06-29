package com.jcute.core.toolkit.cycle.support;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import com.jcute.core.toolkit.cycle.EventableCallBack;
import com.jcute.core.toolkit.cycle.Stable;
import com.jcute.core.toolkit.cycle.StableEvent;
import com.jcute.core.toolkit.cycle.StableListener;
import com.jcute.core.toolkit.cycle.StableType;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.util.StringUtils;

public abstract class AbstractStable<E extends StableEvent,L extends StableListener<E>> implements Stable<E,L>{

	private static final Logger logger = LoggerFactory.getLogger(AbstractStable.class);
	private static final String EVENT_START_SUCCESS = "EVENT_START_SUCCESS";
	private static final String EVENT_START_FAILURE = "EVENT_START_FAILURE";
	private static final String EVENT_CLOSE_SUCCESS = "EVENT_CLOSE_SUCCESS";
	private static final String EVENT_CLOSE_FAILURE = "EVENT_CLOSE_FAILURE";
	private static final String EVENT_STARTING = "EVENT_STARTING";
	private static final String EVENT_CLOSING = "EVENT_CLOSING";

	private ReentrantLock locker = new ReentrantLock();
	private Map<String,Set<L>> listeners = new ConcurrentHashMap<String,Set<L>>();
	private volatile StableType type = StableType.Closed;

	@Override
	public void attachListener(String eventName,L listener){
		if(StringUtils.isEmpty(eventName)){
			logger.warn("missing event name");
			return;
		}
		if(null == listener){
			logger.warn("missing event listener");
			return;
		}
		try{
			this.locker.lock();
			Set<L> sets = this.listeners.get(eventName);
			if(null == sets){
				this.listeners.put(eventName,new LinkedHashSet<L>());
				sets = this.listeners.get(eventName);
			}
			if(sets.contains(listener)){
				logger.debug("exists event listener {}",listener);
				return;
			}
			sets.add(listener);
		}finally{
			this.locker.unlock();
		}
	}

	@Override
	public void detachListener(String eventName,L listener){
		if(StringUtils.isEmpty(eventName)){
			logger.warn("missing event name");
			return;
		}
		if(null == listener){
			logger.warn("missing event listener");
			return;
		}
		try{
			this.locker.lock();
			Set<L> sets = this.listeners.get(eventName);
			if(null == sets || sets.size() == 0){
				this.listeners.remove(eventName);
				return;
			}
			if(sets.contains(listener)){
				sets.remove(listener);
			}
		}finally{
			this.locker.unlock();
		}
	}

	@Override
	public void fireEvent(String eventName){
		this.fireEvent(eventName,null);
	}
	
	@Override
	public void fireEvent(String eventName,EventableCallBack<E> callback){
		if(StringUtils.isEmpty(eventName)){
			logger.warn("event name is empty");
			return;
		}
		try{
			this.locker.lock();
			Set<L> sets = this.listeners.get(eventName);
			if(null == sets || sets.size() == 0){
				this.listeners.remove(eventName);
				return;
			}
			for(L listener : sets){
				E event = this.createEvent();
				if(null != callback){
					event = callback.callback(event);
				}
				this.doExecute(listener,event);
			}
		}finally{
			this.locker.unlock();
		}
	}
	
	@Override
	public final synchronized void start() throws Exception{
		if(this.isStarting() || this.isRunning()){
			logger.debug("can not be start ,because status is {}",this.getType());
			return;
		}
		try{
			this.type = StableType.Staring;
			this.fireEvent(EVENT_STARTING);
			this.doStart();
			this.type = StableType.Running;
			this.fireEvent(EVENT_START_SUCCESS);
		}catch(Exception e){
			this.type = StableType.Failed;
			this.fireEvent(EVENT_START_FAILURE);
			throw e;
		}
	}

	@Override
	public final synchronized void close() throws Exception{
		if(this.isClosing() || this.isClosed()){
			logger.debug("can not be close ,because status is {}",this.getType());
			return;
		}
		try{
			this.type = StableType.Closing;
			this.fireEvent(EVENT_CLOSING);
			this.doClose();
			this.type = StableType.Closed;
			this.fireEvent(EVENT_CLOSE_SUCCESS);
		}catch(Exception e){
			this.type = StableType.Failed;
			this.fireEvent(EVENT_CLOSE_FAILURE);
			throw e;
		}
	}

	@Override
	public StableType getType(){
		return this.type;
	}

	@Override
	public boolean isRunning(){
		return this.type == StableType.Running;
	}

	@Override
	public boolean isStarting(){
		return this.type == StableType.Staring;
	}

	@Override
	public boolean isClosing(){
		return this.type == StableType.Closing;
	}

	@Override
	public boolean isClosed(){
		return this.type == StableType.Closed;
	}

	@Override
	public boolean isFailed(){
		return this.type == StableType.Failed;
	}
	
	@Override
	public void attachStartingListener(L listener){
		this.attachListener(EVENT_STARTING,listener);
	}

	@Override
	public void detachStartingListener(L listener){
		this.detachListener(EVENT_STARTING,listener);
	}

	@Override
	public void attachStartSuccessListener(L listener){
		this.attachListener(EVENT_START_SUCCESS,listener);
	}

	@Override
	public void detachStartSuccessListener(L listener){
		this.detachListener(EVENT_START_SUCCESS,listener);
	}

	@Override
	public void attachStartFailureListener(L listener){
		this.attachListener(EVENT_START_FAILURE,listener);
	}

	@Override
	public void detachStartFaulureListener(L listener){
		this.detachListener(EVENT_START_FAILURE,listener);
	}
	
	@Override
	public void attachClosingListener(L listener){
		this.attachListener(EVENT_CLOSING,listener);
	}

	@Override
	public void detachClosingListener(L listener){
		this.detachListener(EVENT_CLOSING,listener);
	}

	@Override
	public void attachCloseSuccessListener(L listener){
		this.attachListener(EVENT_CLOSE_SUCCESS,listener);
	}

	@Override
	public void detachCloseSuccessListener(L listener){
		this.detachListener(EVENT_CLOSE_SUCCESS,listener);
	}

	@Override
	public void attachCloseFailureListener(L listener){
		this.attachListener(EVENT_CLOSE_FAILURE,listener);
	}

	@Override
	public void detachCloseFailureListener(L listener){
		this.detachListener(EVENT_CLOSE_FAILURE,listener);
	}

	protected void doExecute(L listener,E event){
		try{
			if(null != listener){
				listener.execute(event);
			}
		}catch(Exception e){
			logger.warn("execute listener failed",e);
		}
	}
	
	protected abstract E createEvent();
	
	protected abstract void doStart() throws Exception;

	protected abstract void doClose() throws Exception;

}