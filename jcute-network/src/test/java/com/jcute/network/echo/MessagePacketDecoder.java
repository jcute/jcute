package com.jcute.network.echo;

import java.nio.ByteBuffer;

import com.jcute.network.packet.Packet;
import com.jcute.network.packet.PacketDecoder;

public class MessagePacketDecoder implements PacketDecoder{

	@Override
	public Packet decode(ByteBuffer buffer){
		if(buffer.hasRemaining()){
			int length = buffer.getInt();
			if(length > 0){
				byte[] data = new byte[length];
				buffer.get(data);
				return new MessagePacket(new String(data));
			}
		}
		return null;
	}

}