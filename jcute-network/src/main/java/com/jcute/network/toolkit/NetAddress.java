package com.jcute.network.toolkit;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class NetAddress{

	private static final String HOST = "0.0.0.0";
	private static final int PORT = 0;

	private final String host;
	private final int port;

	private NetAddress(String host,int port){
		if(null == host || host.trim().length() == 0){
			host = HOST;
		}
		if(port < 0 || port > 65535){
			port = PORT;
		}
		this.host = host;
		this.port = port;
	}

	public String getHost(){
		return host;
	}

	public int getPort(){
		return port;
	}

	public SocketAddress toSocketAddress(){
		return new InetSocketAddress(this.host,this.port);
	}

	@Override
	public int hashCode(){
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(null == obj){
			return false;
		}
		if(obj instanceof NetAddress){
			return obj.toString().equals(this.toString());
		}
		return false;
	}

	@Override
	public String toString(){
		return String.format("%s:%d",this.host,this.port);
	}

	public static NetAddress create(String host,int port){
		return new NetAddress(host,port);
	}

	public static NetAddress create(String host){
		return new NetAddress(host,PORT);
	}

	public static NetAddress create(int port){
		return new NetAddress(HOST,port);
	}

	public static NetAddress create(){
		return new NetAddress(HOST,PORT);
	}

	public static NetAddress create(InetSocketAddress address){
		return new NetAddress(address.getHostName(),address.getPort());
	}
	
}