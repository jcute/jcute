package com.jcute.network;

import com.jcute.network.packet.Packet;

public class ByteArrayPacket implements Packet{
	
	private byte[] data;
	public ByteArrayPacket(byte[] data){
		this.data = data;
	}
	
	public byte[] getData(){
		return data;
	}
	
}