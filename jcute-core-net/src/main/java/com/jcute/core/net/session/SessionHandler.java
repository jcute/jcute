package com.jcute.core.net.session;

public interface SessionHandler{

	public void onSessionStart(Session session) throws Exception;

	public void onSessionClose(Session session) throws Exception;

	public void onSessionTimeout(Session session) throws Exception;

	public void onMessageReceive(Session session,Object message) throws Exception;

	public void onMessageSent(Session session,Object message) throws Exception;

	public void onExceptionCaught(Session session,Throwable cause);

}