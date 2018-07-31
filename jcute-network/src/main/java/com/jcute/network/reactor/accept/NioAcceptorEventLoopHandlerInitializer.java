package com.jcute.network.reactor.accept;


public interface NioAcceptorEventLoopHandlerInitializer{

	public void initHandler(NioAcceptorEventLoopChain chain);
	
}