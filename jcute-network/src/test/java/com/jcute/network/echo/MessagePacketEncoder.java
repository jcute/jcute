package com.jcute.network.echo;

import java.nio.ByteBuffer;

import com.jcute.network.packet.Packet;
import com.jcute.network.packet.PacketEncoder;

public class MessagePacketEncoder implements PacketEncoder{

	@Override
	public ByteBuffer encode(Packet packet){
		if(null != packet){
			MessagePacket messagePacket = (MessagePacket)packet;
			byte[] data = messagePacket.getMessage().getBytes();
			ByteBuffer buffer = ByteBuffer.allocate(data.length + 4);
			buffer.putInt(data.length);
			buffer.put(data);
			return buffer;
		}
		return null;
	}

}