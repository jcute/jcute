package com.jcute.network.reactor.accept.support;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.jcute.network.reactor.accept.NioAcceptorEventLoop;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopChain;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopContext;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopHandler;
import com.jcute.network.toolkit.handler.support.AbstractHandlerChain;

public class DefaultNioAcceptorEventLoopChain extends AbstractHandlerChain<NioAcceptorEventLoopHandler,NioAcceptorEventLoopContext,NioAcceptorEventLoopChain> implements NioAcceptorEventLoopChain{

	private NioAcceptorEventLoop nioAcceptorEventLoop;

	public DefaultNioAcceptorEventLoopChain(NioAcceptorEventLoop nioAcceptorEventLoop){
		this.nioAcceptorEventLoop = nioAcceptorEventLoop;
	}

	
	@Override
	public NioAcceptorEventLoop getNioAcceptorEventLoop(){
		return this.nioAcceptorEventLoop;
	}
	
	@Override
	public void fireOnAccept(ServerSocketChannel serverChannel){
		NioAcceptorEventLoopContext context = this.head.getNext();
		while(context != this.foot){
			if(context.getHandler().onAccept(this.nioAcceptorEventLoop,serverChannel) == false){
				break;
			}
			context = context.getNext();
		}
	}

	@Override
	public void fireOnSelect(ServerSocketChannel serverChannel,SocketChannel clientChannel){
		NioAcceptorEventLoopContext context = this.head.getNext();
		while(context != this.foot){
			if(context.getHandler().onSelect(this.nioAcceptorEventLoop,serverChannel,clientChannel) == false){
				break;
			}
			context = context.getNext();
		}
	}

	@Override
	protected NioAcceptorEventLoopContext createHandlerContext(String name,NioAcceptorEventLoopHandler handler){
		return new DefaultNioAcceptorEventLoopContext(this,name,handler);
	}

	@Override
	protected NioAcceptorEventLoopContext createHeadHandlerContext(){
		return new DefaultNioAcceptorEventLoopHeadHandler(this);
	}

	@Override
	protected NioAcceptorEventLoopContext createFootHandlerContext(){
		return new DefaultNioAcceptorEventLoopFootHandler(this);
	}

}