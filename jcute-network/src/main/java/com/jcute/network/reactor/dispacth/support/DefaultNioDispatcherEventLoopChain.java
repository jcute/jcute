package com.jcute.network.reactor.dispacth.support;

import java.nio.channels.Selector;

import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopChain;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopContext;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopHandler;
import com.jcute.network.toolkit.handler.support.AbstractHandlerChain;

public class DefaultNioDispatcherEventLoopChain extends AbstractHandlerChain<NioDispatcherEventLoopHandler,NioDispatcherEventLoopContext,NioDispatcherEventLoopChain> implements NioDispatcherEventLoopChain{

	private NioDispatcherEventLoop nioDispatcherEventLoop;

	public DefaultNioDispatcherEventLoopChain(NioDispatcherEventLoop nioDispatcherEventLoop){
		this.nioDispatcherEventLoop = nioDispatcherEventLoop;
	}

	@Override
	public NioDispatcherEventLoop getNioDispatcherEventLoop(){
		return this.nioDispatcherEventLoop;
	}

	@Override
	protected NioDispatcherEventLoopContext createHandlerContext(String name,NioDispatcherEventLoopHandler handler){
		return new DefaultNioDispatcherEventLoopContext(this,name,handler);
	}

	@Override
	protected NioDispatcherEventLoopContext createHeadHandlerContext(){
		return new DefaultNioDispatcherEventLoopHeadHandler(this);
	}

	@Override
	protected NioDispatcherEventLoopContext createFootHandlerContext(){
		return new DefaultNioDispatcherEventLoopFootHandler(this);
	}

	@Override
	public void fireOnConnect(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector){
		NioDispatcherEventLoopContext context = this.head.getNext();
		while(context != this.foot){
			if(context.getHandler().onConnect(this.nioDispatcherEventLoop,connection,selector) == false){
				break;
			}
			context = context.getNext();
		}
	}

	@Override
	public void fireOnRegist(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector){
		NioDispatcherEventLoopContext context = this.head.getNext();
		while(context != this.foot){
			if(context.getHandler().onRegist(this.nioDispatcherEventLoop,connection,selector) == false){
				break;
			}
			context = context.getNext();
		}
	}

	@Override
	public void fireOnReader(NioDispatcherEventLoop eventLoop,Connection connection){
		NioDispatcherEventLoopContext context = this.head.getNext();
		while(context != this.foot){
			if(context.getHandler().onReader(this.nioDispatcherEventLoop,connection) == false){
				break;
			}
			context = context.getNext();
		}
	}

	@Override
	public void fireOnWriter(NioDispatcherEventLoop eventLoop,Connection connection){
		NioDispatcherEventLoopContext context = this.head.getNext();
		while(context != this.foot){
			if(context.getHandler().onWriter(this.nioDispatcherEventLoop,connection) == false){
				break;
			}
			context = context.getNext();
		}
	}

	@Override
	public void fireOnCaught(NioDispatcherEventLoop eventLoop,Connection connection,Throwable cause){
		NioDispatcherEventLoopContext context = this.head.getNext();
		while(context != this.foot){
			if(context.getHandler().onCaught(this.nioDispatcherEventLoop,connection,cause) == false){
				break;
			}
			context = context.getNext();
		}
	}

}