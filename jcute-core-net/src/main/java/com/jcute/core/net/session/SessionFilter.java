package com.jcute.core.net.session;

import com.jcute.core.net.message.Message;

public interface SessionFilter{

	public void sessionStart(SessionFilterChain filterChain) throws Exception;

	public void sessionClose(SessionFilterChain filterChain) throws Exception;

	public void sessionTimeout(SessionFilterChain fileChain) throws Exception;

	public void messageReceive(SessionFilterChain filterChain,Message message) throws Exception;

	public void messageSend(SessionFilterChain filterChain,Message message) throws Exception;

	public void messageSent(SessionFilterChain filterChain,Message message) throws Exception;

	public void objectReceive(SessionFilterChain filterChain,Object message) throws Exception;

	public void objectSent(SessionFilterChain filterChain,Object message) throws Exception;

	public void exceptionCaught(SessionFilterChain filterChain,Throwable cause);

}