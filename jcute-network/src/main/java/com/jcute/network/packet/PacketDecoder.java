package com.jcute.network.packet;

import java.nio.ByteBuffer;

public interface PacketDecoder{
	
	public Packet decode(ByteBuffer buffer);
	
}