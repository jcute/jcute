package com.jcute.core.net.session.support;

import com.jcute.core.net.NetAddress;
import com.jcute.core.net.concurrent.DispatcherManager;
import com.jcute.core.net.concurrent.Future;
import com.jcute.core.net.concurrent.support.DefaultDispatcherManager;
import com.jcute.core.net.concurrent.support.DefaultFuture;
import com.jcute.core.net.message.Message;
import com.jcute.core.net.message.MessageDecoder;
import com.jcute.core.net.message.MessageEncoder;
import com.jcute.core.net.message.support.DefaultMessageDecoder;
import com.jcute.core.net.message.support.DefaultMessageEncoder;
import com.jcute.core.net.session.Session;
import com.jcute.core.net.session.SessionFilter;
import com.jcute.core.net.session.SessionFilterChain;
import com.jcute.core.net.session.SessionHandler;
import com.jcute.core.net.toolkit.support.AbstractConfigurable;

public abstract class AbstractSession extends AbstractConfigurable implements Session{
	
	private static final SessionFilter[] EMPTY_FILTERS = new SessionFilter[0];
	
	private volatile SessionFilter[] filters = EMPTY_FILTERS;
	
	private NetAddress localAddress;
	private NetAddress removeAddress;
	
	private MessageDecoder messageDecoder = new DefaultMessageDecoder();
	private MessageEncoder messageEncoder = new DefaultMessageEncoder();
	private DispatcherManager dispatcherManager = new DefaultDispatcherManager();
	
	private SessionHandler sessionHandler;
	
	private int readMessageSize = 8192;
	private int sessionTimeout = 0;
	
	@Override
	public NetAddress getRemoteAddress(){
		return this.removeAddress;
	}

	@Override
	public void setRemoteAddress(NetAddress netAddress){
		if(this.isRunning()){
			throw new IllegalStateException("can't set remote address after session running");
		}
		this.removeAddress = netAddress;
	}
	
	@Override
	public NetAddress getLocalAddress(){
		return this.localAddress;
	}

	@Override
	public void setLocalAddress(NetAddress netAddress){
		if(this.isRunning()){
			throw new IllegalStateException("can't set local address after session running");
		}
		this.localAddress = netAddress;
	}

	@Override
	public MessageDecoder getMessageDecoder(){
		return this.messageDecoder;
	}

	@Override
	public void setMessageDecoder(MessageDecoder messageDecoder){
		if(null == messageDecoder){
			return;
		}
		this.messageDecoder = messageDecoder;
	}

	@Override
	public MessageEncoder getMessageEncoder(){
		return this.messageEncoder;
	}

	@Override
	public void setMessageEncoder(MessageEncoder messageEncoder){
		if(null == messageEncoder){
			return;
		}
		this.messageEncoder = messageEncoder;
	}

	@Override
	public void setSessionTimeout(int timeout){
		this.sessionTimeout = timeout;
	}

	@Override
	public int getSessionTimeout(){
		return this.sessionTimeout;
	}

	@Override
	public SessionFilterChain getSessionFilterChain(boolean reversed){
		return new DefaultSessionFilterChain(this,reversed,null,this.filters);
	}
	
	@Override
	public void attachSessionFilter(SessionFilter filter){
		this.attachSessionFilter(filter,this.filters.length);
	}
	
	@Override
	public void attachSessionFilter(SessionFilter filter,int index){
		if(null == filter){
			return;
		}
		index = Math.max(this.filters.length,index);
		SessionFilter[] newFilters = new SessionFilter[this.filters.length + 1];
		System.arraycopy(this.filters,0,newFilters,0,index);
		System.arraycopy(this.filters,index,newFilters,index + 1,filters.length - index);
		newFilters[index] = filter;
		this.filters = newFilters;
	}
	
	@Override
	public void detachSessionFilter(SessionFilter filter){
		if(null == filter){
			return;
		}
		for(int i=0;i<this.filters.length;i++){
			if(this.filters[i].equals(filter)){
				SessionFilter[] newFilters = new SessionFilter[this.filters.length - 1];
				System.arraycopy(this.filters,0,newFilters,0,i);
				System.arraycopy(this.filters,i + 1,newFilters,i,newFilters.length - i);
				this.filters = newFilters;
				break;
			}
		}
	}

	@Override
	public SessionFilter getSessionFilter(int index){
		if(index < 0 || index >= filters.length){
			return null;
		}
		return this.filters[index];
	}

	@Override
	public SessionFilter[] getSessionFilters(){
		SessionFilter[] filters = this.filters;
		SessionFilter[] result = new SessionFilter[filters.length];
		System.arraycopy(filters,0,result,0,filters.length);
		return result;
	}

	@Override
	public void setSessionHandler(SessionHandler handler){
		if(null == handler){
			return;
		}
		this.sessionHandler = handler;
	}

	@Override
	public SessionHandler getSessionHandler(){
		return this.sessionHandler;
	}

	@Override
	public Future flush(Message message){
		return this.flush(message,0);
	}

	@Override
	public Future flush(Message message,int priority){
		return this.send(null,message,priority);
	}

	@Override
	public Future send(Object message){
		return this.send(message,0);
	}

	@Override
	public Future send(Object message,int priority){
		Message msg = null;
		try{
			msg = this.messageEncoder.encode(this,message);
		}catch(Exception e){
			this.dispatchException(e);
			return new DefaultFuture(this,this.dispatcherManager,false);
		}
		return this.send(message,msg,priority);
	}
	
	protected DispatcherManager getDispatcherManager(){
		return this.dispatcherManager;
	}
	
	protected void dispatchException(Throwable cause){
		this.getSessionFilterChain(false).onExceptionCaught(cause);
	}
	
	protected abstract Future send(Object data,Message message,int priority);
	
}