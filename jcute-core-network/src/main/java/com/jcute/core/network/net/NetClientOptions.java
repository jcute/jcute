package com.jcute.core.network.net;

import java.nio.charset.Charset;

public interface NetClientOptions{
	
	public Charset getCharset();
	public void setCharset(Charset charset);
	
	public NetClientHandlerFactory getHandlerFactory();
	public NetClientDecoder getNetClientDecoder();
	public NetClientEncoder getNetClientEncoder();
	public NetClientHandler getNetClientHandler();
	public void setHandlerFactory(NetClientHandlerFactory handlerFactory);
	
}