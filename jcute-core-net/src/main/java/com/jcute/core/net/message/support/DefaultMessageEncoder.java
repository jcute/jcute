package com.jcute.core.net.message.support;

import com.jcute.core.net.message.Message;
import com.jcute.core.net.message.MessageEncoder;
import com.jcute.core.net.session.Session;

public class DefaultMessageEncoder implements MessageEncoder{

	@Override
	public Message encode(Session session,Object data) throws Exception{
		if(data instanceof Message){
			return (Message)data;
		}
		return null;
	}
	
}