package com.jcute.core.network.net.support;

import java.nio.charset.Charset;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.net.NetServerDecoder;
import com.jcute.core.network.net.NetServerEncoder;
import com.jcute.core.network.net.NetServerHandler;
import com.jcute.core.network.net.NetServerHandlerFactory;
import com.jcute.core.network.net.NetServerOptions;

public abstract class AbstractNetServerOptions implements NetServerOptions{

	protected NetWorkAddress netWorkAddress;
	protected NetServerHandlerFactory netServerHandlerFactory;
	protected int acceptBackLog;
	protected Charset charset;

	public AbstractNetServerOptions(NetServerHandlerFactory netServerHandlerFactory){
		if(null == netServerHandlerFactory){
			netServerHandlerFactory = this.doCreateDefaultNetServerHandlerFactory(this);
		}
		this.netServerHandlerFactory = netServerHandlerFactory;
		this.netWorkAddress = this.doCreateDefaultNetWorkAddress();
		this.acceptBackLog = 1024;
		this.charset = Charset.forName("UTF-8");
	}

	@Override
	public NetServerHandlerFactory getHandlerFactory(){
		return this.netServerHandlerFactory;
	}

	@Override
	public NetServerHandler getNetServerHandler(){
		return this.netServerHandlerFactory.createHandler();
	}

	@Override
	public NetServerEncoder getNetServerEncoder(){
		return this.netServerHandlerFactory.createEncoder();
	}

	@Override
	public NetServerDecoder getNetServerDecoder(){
		return this.netServerHandlerFactory.createDecoder();
	}
	
	
	
	@Override
	public NetWorkAddress getNetWorkAddress(){
		return this.netWorkAddress;
	}

	@Override
	public int getAcceptBackLog(){
		return this.acceptBackLog;
	}

	@Override
	public void setNetServerHandlerFactory(NetServerHandlerFactory handlerFactory){
		if(null == handlerFactory){
			return;
		}
		this.netServerHandlerFactory = handlerFactory;
	}

	@Override
	public void setNetWorkAddress(NetWorkAddress netWorkAddress){
		if(null == netWorkAddress){
			return;
		}
		this.netWorkAddress = netWorkAddress;
	}

	@Override
	public void setAcceptBackLog(int backLog){
		this.acceptBackLog = backLog;
	}
	
	@Override
	public Charset getCharset(){
		return this.charset;
	}

	@Override
	public void setCharset(Charset charset){
		if(null == charset){
			return;
		}
		this.charset = charset;
	}

	protected abstract NetWorkAddress doCreateDefaultNetWorkAddress();
	protected abstract NetServerHandlerFactory doCreateDefaultNetServerHandlerFactory(NetServerOptions netServerOptions);

}