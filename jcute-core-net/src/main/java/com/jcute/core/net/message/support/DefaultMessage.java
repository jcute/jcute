package com.jcute.core.net.message.support;

import com.jcute.core.net.NetAddress;
import com.jcute.core.net.buffer.Buffer;
import com.jcute.core.net.message.Message;

public class DefaultMessage implements Message{
	
	private Buffer content;
	private NetAddress address;
	
	@Override
	public NetAddress getAddress(){
		return this.address;
	}

	@Override
	public Buffer getContent(){
		return this.content;
	}
	
}