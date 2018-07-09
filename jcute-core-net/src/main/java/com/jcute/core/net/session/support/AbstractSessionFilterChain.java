package com.jcute.core.net.session.support;

import com.jcute.core.net.message.Message;
import com.jcute.core.net.session.SessionFilter;
import com.jcute.core.net.session.SessionFilterChain;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public abstract class AbstractSessionFilterChain implements SessionFilterChain{

	private static final Logger logger = LoggerFactory.getLogger(AbstractSessionFilterChain.class);

	private static final SessionFilter blankSessionFilter = new DefaultBlankSessionFilter();

	@Override
	public void onSessionStart(){
		try{
			this.getNextSessionFilter().sessionStart(this);
		}catch(Throwable e){
			this.caughtException(e);
		}
	}

	@Override
	public void onSessionClose(){
		try{
			this.getNextSessionFilter().sessionClose(this);
		}catch(Throwable e){
			this.caughtException(e);
		}
	}

	@Override
	public void onSessionTimeout(){
		try{
			this.getNextSessionFilter().sessionTimeout(this);
		}catch(Throwable e){
			this.caughtException(e);
		}
	}

	@Override
	public void onMessageReceive(Message message){
		try{
			this.getNextSessionFilter().messageReceive(this,message);
		}catch(Throwable e){
			this.caughtException(e);
		}
	}

	@Override
	public void onObjectReceive(Object message){
		try{
			this.getNextSessionFilter().objectReceive(this,message);
		}catch(Throwable e){
			this.caughtException(e);
		}
	}

	@Override
	public void onMessageSend(Message message){
		try{
			this.getNextSessionFilter().messageSend(this,message);
		}catch(Throwable e){
			this.caughtException(e);
		}
	}

	@Override
	public void onMessageSent(Message message){
		try{
			this.getNextSessionFilter().messageSent(this,message);
		}catch(Throwable e){
			this.caughtException(e);
		}
	}

	@Override
	public void onObjectSent(Object message){
		try{
			this.getNextSessionFilter().objectSent(this,message);
		}catch(Throwable e){
			this.caughtException(e);
		}
	}

	@Override
	public void onExceptionCaught(Throwable cause){
		try{
			this.getNextSessionFilter().exceptionCaught(this,cause);
		}catch(Throwable t){
			logger.error(t.getMessage(),t);
		}
	}

	protected SessionFilter getNextSessionFilter(){
		SessionFilter sessionFilter = this.nextFilter();
		return sessionFilter == null ? blankSessionFilter : sessionFilter;
	}

	protected void caughtException(Throwable cause){
		this.getSession().getSessionFilterChain(false).onExceptionCaught(cause);
	}

	protected abstract SessionFilter nextFilter();

}