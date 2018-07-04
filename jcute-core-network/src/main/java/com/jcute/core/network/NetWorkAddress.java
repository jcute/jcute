package com.jcute.core.network;

import java.net.InetSocketAddress;

import com.jcute.core.util.StringUtils;

public class NetWorkAddress{
	
	private String host;
	private int port;
	private InetSocketAddress inetSocketAddress;

	public NetWorkAddress(String host,int port){
		if(StringUtils.isEmpty(host)){
			host = "0.0.0.0";
		}
		if(port < 0 || port > 65535){
			port = 0;
		}
		this.host = host;
		this.port = port;
		this.inetSocketAddress = new InetSocketAddress(this.host,this.port);
	}

	public String getHost(){
		return this.host;
	}

	public int getPort(){
		return this.port;
	}

	public InetSocketAddress getInetSocketAddress(){
		return this.inetSocketAddress;
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
		if(obj instanceof NetWorkAddress){
			return this.toString().equals(obj.toString());
		}
		return false;
	}

	@Override
	public String toString(){
		return String.format("%s:%d",this.host,this.port);
	}

	public static NetWorkAddress create(String host,int port){
		return new NetWorkAddress(host,port);
	}

	public static NetWorkAddress create(String host){
		return new NetWorkAddress(host,0);
	}

	public static NetWorkAddress create(int port){
		return new NetWorkAddress("0.0.0.0",port);
	}

	public static NetWorkAddress create(){
		return new NetWorkAddress("0.0.0.0",0);
	}

	public static NetWorkAddress create(InetSocketAddress inetSocketAddress){
		return new NetWorkAddress(inetSocketAddress.getHostName(),inetSocketAddress.getPort());
	}
	
}