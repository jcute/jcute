package com.jcute.network.toolkit.handler;

public interface HandlerContext<T extends Handler,C extends HandlerContext<T,C,A>,A extends HandlerChain<T,C,A>>{
	
	public String getName();
	
	public T getHandler();
	
	public C getNext();
	public C getPrev();
	
	public void setNext(C next);
	public void setPrev(C prev);
	
	public A getChain();
	
}