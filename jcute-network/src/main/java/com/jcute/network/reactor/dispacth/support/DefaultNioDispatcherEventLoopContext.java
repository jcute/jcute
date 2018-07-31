package com.jcute.network.reactor.dispacth.support;

import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopChain;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopContext;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopHandler;
import com.jcute.network.toolkit.handler.support.AbstractHandlerContext;

public class DefaultNioDispatcherEventLoopContext extends AbstractHandlerContext<NioDispatcherEventLoopHandler,NioDispatcherEventLoopContext,NioDispatcherEventLoopChain> implements NioDispatcherEventLoopContext{
	
	public DefaultNioDispatcherEventLoopContext(NioDispatcherEventLoopChain chain,String name,NioDispatcherEventLoopHandler handler){
		super(chain,name,handler);
	}
	
	
}