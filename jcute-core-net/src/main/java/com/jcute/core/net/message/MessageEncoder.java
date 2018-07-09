package com.jcute.core.net.message;

import com.jcute.core.net.session.Session;

public interface MessageEncoder{
	
	public Message encode(Session session,Object data)throws Exception;
	
}