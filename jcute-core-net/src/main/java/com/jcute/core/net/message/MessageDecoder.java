package com.jcute.core.net.message;

import com.jcute.core.net.session.Session;

public interface MessageDecoder{
	
	public Object decode(Session session,Message message)throws Exception;
	
}