package com.jcute.core.net;

import java.io.Serializable;
import java.net.InetSocketAddress;

import com.jcute.core.util.StringUtils;

public final class NetAddress implements Serializable{

	private static final long serialVersionUID = 5803724313027933397L;
	private static final String DEFAULT_HOST = "0.0.0.0";
	private static final int DEFAULT_PORT = 0;

	private final String host;
	private final int port;
	private final InetSocketAddress address;

	private NetAddress(String host,int port){
		if(StringUtils.isEmpty(host)){
			host = DEFAULT_HOST;
		}
		if(port < 0 || port > 65535){
			port = DEFAULT_PORT;
		}
		this.host = host;
		this.port = port;
		this.address = new InetSocketAddress(this.host,this.port);
	}

	public String getHost(){
		return this.host;
	}

	public int getPort(){
		return this.port;
	}

	public InetSocketAddress getInetSocketAddress(){
		return this.address;
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
			return this.toString().equals(obj.toString());
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
		return new NetAddress(host,DEFAULT_PORT);
	}

	public static NetAddress create(int port){
		return new NetAddress(DEFAULT_HOST,port);
	}

	public static NetAddress create(){
		return new NetAddress(DEFAULT_HOST,DEFAULT_PORT);
	}

	public static NetAddress create(InetSocketAddress inetSocketAddress){
		return new NetAddress(inetSocketAddress.getHostName(),inetSocketAddress.getPort());
	}

}