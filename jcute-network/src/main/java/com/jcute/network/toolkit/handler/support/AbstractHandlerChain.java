package com.jcute.network.toolkit.handler.support;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jcute.network.toolkit.handler.Handler;
import com.jcute.network.toolkit.handler.HandlerChain;
import com.jcute.network.toolkit.handler.HandlerContext;

public abstract class AbstractHandlerChain<T extends Handler,C extends HandlerContext<T,C,A>,A extends HandlerChain<T,C,A>> implements HandlerChain<T,C,A>{

	protected C head;
	protected C foot;

	public AbstractHandlerChain(){
		this.head = this.createHeadHandlerContext();
		this.foot = this.createFootHandlerContext();
		this.head.setNext(this.foot);
		this.foot.setPrev(this.head);
	}

	@Override
	public Iterator<Entry<String,T>> iterator(){
		return this.getHandlers().entrySet().iterator();
	}

	@Override
	public void attachFirst(String name,T handler){
		final C context;
		synchronized(this){
			context = this.createHandlerContext(name,handler);
			this.doAttachFirst(context);
		}
	}

	@Override
	public void attachLast(String name,T handler){
		final C context;
		synchronized(this){
			context = this.createHandlerContext(name,handler);
			this.doAttachLast(context);
		}
	}

	@Override
	public void attachBefore(String target,String name,T handler){
		final C nContext;
		final C oContext;
		synchronized(this){
			oContext = this.getContext(target);
			nContext = this.createHandlerContext(name,handler);
			if(null == oContext){
				throw new IllegalArgumentException("old context not found : [ " + target + "]");
			}
			this.doAttachBefore(nContext,oContext);
		}
	}

	@Override
	public void attachAfter(String target,String name,T handler){
		final C nContext;
		final C oContext;
		synchronized(this){
			oContext = this.getContext(target);
			nContext = this.createHandlerContext(name,handler);
			if(null == oContext){
				throw new IllegalArgumentException("old context not found : [ " + target + "]");
			}
			this.doAttachAfter(nContext,oContext);
		}
	}

	@Override
	public void detachHandler(String name){
		C context = this.getContext(name);
		if(null != context){
			context.getPrev().setNext(context.getNext());
			context.getNext().setPrev(context.getPrev());
		}
	}

	@Override
	public void detachHandler(T handler){
		C context = this.getContext(handler);
		if(null != context){
			context.getPrev().setNext(context.getNext());
			context.getNext().setPrev(context.getPrev());
		}
	}

	@Override
	public void replaceHandler(String target,String name,T handler){
		C context = this.getContext(target);
		if(null != context){
			C newContext = this.createHandlerContext(name,handler);
			newContext.setPrev(context.getPrev());
			newContext.setNext(context.getNext());
		}
	}

	@Override
	public boolean containsHandler(String name){
		C context = this.getContext(name);
		return null != context;
	}

	@Override
	public boolean containsHandler(T handler){
		C context = this.getContext(handler);
		return null != context;
	}

	@Override
	public T getFirstHandler(){
		C context = this.getFirstHandlerContext();
		if(null != context){
			return context.getHandler();
		}
		return null;
	}

	@Override
	public T getLastHandler(){
		C context = this.getLastHandlerContext();
		if(null != context){
			return context.getHandler();
		}
		return null;
	}

	@Override
	public T getHandler(String name){
		C context = this.getContext(name);
		if(null != context){
			return context.getHandler();
		}
		return null;
	}

	@Override
	public C getFirstHandlerContext(){
		C context = this.head.getNext();
		if(context == this.foot){
			return null;
		}
		return this.head.getNext();
	}

	@Override
	public C getLastHandlerContext(){
		C context = this.foot.getPrev();
		if(context == this.head){
			return null;
		}
		return this.foot.getPrev();
	}

	@Override
	public Set<String> getHandlerNames(){
		Set<String> names = new LinkedHashSet<String>();
		C context = this.head.getNext();
		for(;;){
			if(this.foot == context){
				return names;
			}
			names.add(context.getName());
			context = context.getNext();
		}
	}

	@Override
	public Map<String,T> getHandlers(){
		Map<String,T> result = new LinkedHashMap<String,T>();
		C context = this.head.getNext();
		for(;;){
			if(this.foot == context){
				return result;
			}
			result.put(context.getName(),context.getHandler());
			context = context.getNext();
		}
	}

	protected void doAttachFirst(C newContext){
		C nextContext = this.head.getNext();
		newContext.setPrev(this.head);
		newContext.setNext(nextContext);
		this.head.setNext(newContext);
		nextContext.setPrev(newContext);
	}

	protected void doAttachLast(C newContext){
		C prevContext = this.foot.getPrev();
		newContext.setPrev(prevContext);
		newContext.setNext(this.foot);
		prevContext.setNext(newContext);
		this.foot.setPrev(newContext);
	}

	protected void doAttachBefore(C newContext,C oldContext){
		newContext.setPrev(oldContext.getPrev());
		newContext.setNext(oldContext);
		oldContext.getPrev().setNext(newContext);
		oldContext.setPrev(newContext);
	}

	protected void doAttachAfter(C newContext,C oldContext){
		newContext.setPrev(oldContext);
		newContext.setNext(oldContext.getNext());
		oldContext.getNext().setPrev(newContext);
		oldContext.setNext(newContext);
	}

	protected C getContext(T handler){
		C context = this.head.getNext();
		while(context != this.foot){
			if(context.getHandler().equals(handler)){
				return context;
			}
			context = context.getNext();
		}
		return null;
	}

	protected C getContext(String name){
		C context = this.head.getNext();
		while(context != this.foot){
			if(context.getName().equals(name)){
				return context;
			}
			context = context.getNext();
		}
		return null;
	}

	protected abstract C createHandlerContext(String name,T handler);

	protected abstract C createHeadHandlerContext();

	protected abstract C createFootHandlerContext();

}