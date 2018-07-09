package com.jcute.core.net.session.support;

import com.jcute.core.net.session.Session;
import com.jcute.core.net.session.SessionHandler;

public class DefaultSessionHandlerAdapter implements SessionHandler{

	@Override
	public void onSessionStart(Session session) throws Exception{

	}

	@Override
	public void onSessionClose(Session session) throws Exception{

	}

	@Override
	public void onSessionTimeout(Session session) throws Exception{

	}

	@Override
	public void onMessageReceive(Session session,Object message) throws Exception{

	}

	@Override
	public void onMessageSent(Session session,Object message) throws Exception{

	}

	@Override
	public void onExceptionCaught(Session session,Throwable cause){

	}

}