package com.jcute.core.toolkit.cycle;

public interface EventableCallBack<E extends EventableEvent>{
	
	public E callback(E event);
	
}