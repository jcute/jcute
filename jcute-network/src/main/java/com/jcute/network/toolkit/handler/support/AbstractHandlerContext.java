package com.jcute.network.toolkit.handler.support;

import com.jcute.network.toolkit.handler.Handler;
import com.jcute.network.toolkit.handler.HandlerChain;
import com.jcute.network.toolkit.handler.HandlerContext;

public abstract class AbstractHandlerContext<T extends Handler,C extends HandlerContext<T,C,A>,A extends HandlerChain<T,C,A>> implements HandlerContext<T,C,A>{

	protected final A chain;
	protected final String name;
	protected final T handler;

	protected volatile C next;
	protected volatile C prev;

	public AbstractHandlerContext(A chain,String name,T handler){
		this.chain = chain;
		this.name = name;
		this.handler = handler;
	}

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public T getHandler(){
		return this.handler;
	}

	@Override
	public C getNext(){
		return this.next;
	}

	@Override
	public C getPrev(){
		return this.prev;
	}

	@Override
	public void setNext(C next){
		this.next = next;
	}

	@Override
	public void setPrev(C prev){
		this.prev = prev;
	}

	@Override
	public A getChain(){
		return this.chain;
	}

}