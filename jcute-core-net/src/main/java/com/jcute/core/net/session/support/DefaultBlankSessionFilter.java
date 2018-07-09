package com.jcute.core.net.session.support;

import com.jcute.core.net.message.Message;
import com.jcute.core.net.session.SessionFilter;
import com.jcute.core.net.session.SessionFilterChain;

public class DefaultBlankSessionFilter implements SessionFilter{

	@Override
	public void sessionStart(SessionFilterChain filterChain) throws Exception{

	}

	@Override
	public void sessionClose(SessionFilterChain filterChain) throws Exception{

	}

	@Override
	public void sessionTimeout(SessionFilterChain fileChain) throws Exception{

	}

	@Override
	public void messageReceive(SessionFilterChain filterChain,Message message) throws Exception{

	}

	@Override
	public void messageSend(SessionFilterChain filterChain,Message message) throws Exception{

	}

	@Override
	public void messageSent(SessionFilterChain filterChain,Message message) throws Exception{

	}

	@Override
	public void objectReceive(SessionFilterChain filterChain,Object message) throws Exception{

	}

	@Override
	public void objectSent(SessionFilterChain filterChain,Object message) throws Exception{

	}

	@Override
	public void exceptionCaught(SessionFilterChain filterChain,Throwable cause){

	}

}