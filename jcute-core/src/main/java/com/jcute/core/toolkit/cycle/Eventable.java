package com.jcute.core.toolkit.cycle;

public interface Eventable<E extends EventableEvent,L extends EventableListener<E>>{

	public void attachListener(String eventName,L listener);

	public void detachListener(String eventName,L listener);

	public void fireEvent(String eventName);
	
	public void fireEvent(String eventName,EventableCallBack<E> callback);

}