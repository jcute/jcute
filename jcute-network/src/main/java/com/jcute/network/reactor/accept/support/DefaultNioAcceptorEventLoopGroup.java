package com.jcute.network.reactor.accept.support;

import com.jcute.network.reactor.accept.NioAcceptorEventLoop;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopChain;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopGroup;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopHandlerInitializer;
import com.jcute.network.toolkit.NetAddress;
import com.jcute.network.toolkit.looper.EventLoop;
import com.jcute.network.toolkit.looper.support.AbstractEventLoopGroup;

public class DefaultNioAcceptorEventLoopGroup extends AbstractEventLoopGroup implements NioAcceptorEventLoopGroup,NioAcceptorEventLoopHandlerInitializer{
	
	private static final String NAME = "accept";
	private static final int SIZE = 1;
	
	private NetAddress bindAddress;
	private NioAcceptorEventLoopHandlerInitializer initializer;
	
	public DefaultNioAcceptorEventLoopGroup(NioAcceptorEventLoopHandlerInitializer initializer,NetAddress bindAddress){
		super(NAME,SIZE);
		this.initializer = initializer;
		this.bindAddress = bindAddress;
	}
	
	public DefaultNioAcceptorEventLoopGroup(NetAddress bindAddress){
		super(NAME,SIZE);
		this.initializer = this;
		this.bindAddress = bindAddress;
	}
	
	@Override
	public NioAcceptorEventLoop getEventLoop(int index){
		return (NioAcceptorEventLoop)super.getEventLoop(index);
	}
	
	@Override
	public NioAcceptorEventLoop getNextEventLoop(){
		return (NioAcceptorEventLoop)super.getNextEventLoop();
	}
	
	@Override
	protected EventLoop doCreateEventLoop(String eventLoopName){
		return new DefaultNioAcceptorEventLoop(this,eventLoopName,this.bindAddress);
	}

	@Override
	protected void onStart() throws Exception{
		int size = this.getEventLoopSize();
		for(int i=0;i<size;i++){
			NioAcceptorEventLoop eventLoop = this.getEventLoop(i);
			this.initializer.initHandler(eventLoop.getHandlerChain());
		}
	}

	@Override
	protected void onClose() throws Exception{
		this.bindAddress = null;
		this.initializer = null;
	}

	@Override
	public void initHandler(NioAcceptorEventLoopChain chain){
		
	}
	
}