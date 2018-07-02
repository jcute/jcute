package com.jcute.core.network.net.support;

import java.nio.charset.Charset;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.net.NetClientDecoder;
import com.jcute.core.network.net.NetClientEncoder;
import com.jcute.core.network.net.NetClientHandler;
import com.jcute.core.network.net.NetClientHandlerFactory;
import com.jcute.core.network.net.NetClientOptions;

public abstract class AbstractNetClientOptions implements NetClientOptions{

	protected NetWorkAddress netWorkAddress;
	protected NetClientHandlerFactory netClientHandlerFactory;
	protected Charset charset;

	public AbstractNetClientOptions(NetClientHandlerFactory netClientHandlerFactory){
		if(null == netClientHandlerFactory){
			netClientHandlerFactory = this.doCreateDefaultNetClientHandlerFactory(this);
		}
		this.netClientHandlerFactory = netClientHandlerFactory;
		this.netWorkAddress = this.doCreateDefaultNetWorkAddress();
		this.charset = Charset.forName("UTF-8");
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

	@Override
	public NetClientHandlerFactory getHandlerFactory(){
		return this.netClientHandlerFactory;
	}

	@Override
	public NetClientDecoder getNetClientDecoder(){
		return this.netClientHandlerFactory.createDecoder();
	}

	@Override
	public NetClientEncoder getNetClientEncoder(){
		return this.netClientHandlerFactory.createEncoder();
	}

	@Override
	public NetClientHandler getNetClientHandler(){
		return this.netClientHandlerFactory.createHandler();
	}
	
	@Override
	public void setHandlerFactory(NetClientHandlerFactory handlerFactory){
		if(null == handlerFactory){
			return;
		}
		this.netClientHandlerFactory = handlerFactory;
	}

	protected abstract NetWorkAddress doCreateDefaultNetWorkAddress();

	protected abstract NetClientHandlerFactory doCreateDefaultNetClientHandlerFactory(NetClientOptions options);

}