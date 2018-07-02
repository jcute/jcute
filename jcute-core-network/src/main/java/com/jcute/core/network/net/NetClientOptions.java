package com.jcute.core.network.net;

import java.nio.charset.Charset;

import com.jcute.core.network.NetWorkAddress;

public interface NetClientOptions{
	
	public Charset getCharset();
	public void setCharset(Charset charset);
	
	public NetWorkAddress getNetWorkAddress();
	public void setNetWorkAddress(NetWorkAddress netWorkAddress);
	
	public NetClientHandlerFactory getHandlerFactory();
	public NetClientDecoder getNetClientDecoder();
	public NetClientEncoder getNetClientEncoder();
	public NetClientHandler getNetClientHandler();
	public void setHandlerFactory(NetClientHandlerFactory handlerFactory);
	
}