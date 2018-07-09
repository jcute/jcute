package com.jcute.core.net.message.support;

import com.jcute.core.net.buffer.Buffer;
import com.jcute.core.net.message.Message;
import com.jcute.core.net.message.MessageDecoder;
import com.jcute.core.net.session.Session;

public class DefaultMessageDecoder implements MessageDecoder{

	@Override
	public Object decode(Session session,Message message) throws Exception{
		Buffer buffer = message.getContent();
		return null;
	}
	
}