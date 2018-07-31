package com.jcute.network.reactor.accept.support;

import com.jcute.network.reactor.accept.NioAcceptorEventLoopChain;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopContext;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopHandler;
import com.jcute.network.toolkit.handler.support.AbstractHandlerContext;

public class DefaultNioAcceptorEventLoopContext extends AbstractHandlerContext<NioAcceptorEventLoopHandler,NioAcceptorEventLoopContext,NioAcceptorEventLoopChain> implements NioAcceptorEventLoopContext{

	public DefaultNioAcceptorEventLoopContext(NioAcceptorEventLoopChain chain,String name,NioAcceptorEventLoopHandler handler){
		super(chain,name,handler);
	}
	
}