package com.jcute.network.echo;

import com.jcute.network.packet.Packet;

public class MessagePacket implements Packet{

	private String message;

	public MessagePacket(){

	}

	public MessagePacket(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message){
		this.message = message;
	}

}