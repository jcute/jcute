package com.jcute.core.net.message;

import com.jcute.core.net.NetAddress;
import com.jcute.core.net.buffer.Buffer;

public interface Message{
	
	public NetAddress getAddress();
	
	public Buffer getContent();
	
}