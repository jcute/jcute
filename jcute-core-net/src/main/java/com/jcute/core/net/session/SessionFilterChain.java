package com.jcute.core.net.session;

import com.jcute.core.net.message.Message;

public interface SessionFilterChain{
	
	public Session getSession();
	
	public void onSessionStart();

	public void onSessionClose();

	public void onSessionTimeout();

	public void onMessageReceive(Message message);

	public void onObjectReceive(Object message);

	public void onMessageSend(Message message);

	public void onMessageSent(Message message);

	public void onObjectSent(Object message);

	public void onExceptionCaught(Throwable cause);
	
}