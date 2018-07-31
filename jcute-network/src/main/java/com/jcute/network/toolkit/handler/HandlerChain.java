package com.jcute.network.toolkit.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public interface HandlerChain<T extends Handler,C extends HandlerContext<T,C,A>,A extends HandlerChain<T,C,A>> extends Iterable<Entry<String,T>>{
	
	public void attachFirst(String name,T handler);
	public void attachLast(String name,T handler);
	public void attachBefore(String target,String name,T handler);
	public void attachAfter(String target,String name,T handler);
	
	public void detachHandler(String name);
	public void detachHandler(T handler);
	public void replaceHandler(String target,String name,T handler);
	public boolean containsHandler(String name);
	public boolean containsHandler(T handler);
	
	public T getFirstHandler();
	public T getLastHandler();
	
	public T getHandler(String name);
	
	public C getFirstHandlerContext();
	public C getLastHandlerContext();
	
	public Set<String> getHandlerNames();
	
	public Map<String,T> getHandlers();
	
}