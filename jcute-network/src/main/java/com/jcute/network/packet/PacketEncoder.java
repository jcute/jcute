package com.jcute.network.packet;

import java.nio.ByteBuffer;

public interface PacketEncoder{

	public ByteBuffer encode(Packet packet);
	
}