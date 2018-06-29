package com.jcute.core.network.net;

import java.nio.charset.Charset;

import com.jcute.core.network.NetWorkAddress;

public interface NetServerOptions{

	public NetServerHandlerFactory getHandlerFactory();
	public NetServerHandler getNetServerHandler();
	public NetServerEncoder getNetServerEncoder();
	public NetServerDecoder getNetServerDecoder();
	public void setNetServerHandlerFactory(NetServerHandlerFactory handlerFactory);
	
	public NetWorkAddress getNetWorkAddress();
	public void setNetWorkAddress(NetWorkAddress netWorkAddress);
	
	public int getAcceptBackLog();
	public void setAcceptBackLog(int backLog);
	
	public Charset getCharset();
	public void setCharset(Charset charset);
	
}