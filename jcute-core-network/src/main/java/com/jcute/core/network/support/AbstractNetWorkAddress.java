package com.jcute.core.network.support;

import java.net.InetSocketAddress;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.util.StringUtils;

public abstract class AbstractNetWorkAddress implements NetWorkAddress{

	private String host = "0.0.0.0";
	private int port = 0;

	public AbstractNetWorkAddress(String host,int port){
		if(StringUtils.isEmpty(host)){
			host = "0.0.0.0";
		}
		if(port < 0 || port > 65535){
			port = 0;
		}
		this.host = host;
		this.port = port;
	}

	@Override
	public String getHost(){
		return this.host;
	}

	@Override
	public int getPort(){
		return this.port;
	}

	@Override
	public InetSocketAddress toSocketAddress(){
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
		if(obj instanceof NetWorkAddress){
			return this.toString().equals(obj.toString());
		}
		return false;
	}

	@Override
	public String toString(){
		return String.format("%s:%d",this.host,this.port);
	}

}