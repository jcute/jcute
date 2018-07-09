package com.jcute.core.net.session;

import com.jcute.core.net.NetAddress;
import com.jcute.core.net.concurrent.Future;
import com.jcute.core.net.message.Message;
import com.jcute.core.net.message.MessageDecoder;
import com.jcute.core.net.message.MessageEncoder;
import com.jcute.core.net.toolkit.Configurable;

public interface Session extends Configurable{

	public SessionType getSessionType();

	public NetAddress getRemoteAddress();
	
	public void setRemoteAddress(NetAddress netAddress);
	
	public NetAddress getLocalAddress();

	public void setLocalAddress(NetAddress netAddress);

	public MessageDecoder getMessageDecoder();

	public void setMessageDecoder(MessageDecoder messageDecoder);

	public MessageEncoder getMessageEncoder();

	public void setMessageEncoder(MessageEncoder messageEncoder);

	public void setSessionTimeout(int timeout);

	public int getSessionTimeout();

	public SessionFilterChain getSessionFilterChain(boolean reversed);

	public void attachSessionFilter(SessionFilter filter);

	public void attachSessionFilter(SessionFilter filter,int index);

	public void detachSessionFilter(SessionFilter filter);

	public SessionFilter getSessionFilter(int index);

	public SessionFilter[] getSessionFilters();

	public void setSessionHandler(SessionHandler handler);

	public SessionHandler getSessionHandler();

	public boolean isRunning();

	public Future start();

	public Future close();

	public Future flush(Message message);

	public Future flush(Message message,int priority);

	public Future send(Object message);

	public Future send(Object message,int priority);

}