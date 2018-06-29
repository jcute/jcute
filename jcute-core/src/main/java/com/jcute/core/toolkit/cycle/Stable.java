package com.jcute.core.toolkit.cycle;

public interface Stable<E extends StableEvent,L extends StableListener<E>> extends Eventable<E,L>{

	public void start() throws Exception;

	public void close() throws Exception;
	
	public StableType getType();
	
	public boolean isRunning();
	
	public boolean isStarting();
	
	public boolean isClosing();
	
	public boolean isClosed();
	
	public boolean isFailed();

	public void attachStartingListener(L listener);
	public void detachStartingListener(L listener);
	
	public void attachStartSuccessListener(L listener);
	public void detachStartSuccessListener(L listener);
	
	public void attachStartFailureListener(L listener);
	public void detachStartFaulureListener(L listener);
	
	public void attachClosingListener(L listener);
	public void detachClosingListener(L listener);
	
	public void attachCloseSuccessListener(L listener);
	public void detachCloseSuccessListener(L listener);
	
	public void attachCloseFailureListener(L listener);
	public void detachCloseFailureListener(L listener);
	
}