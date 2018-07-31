package com.jcute.network.reactor.connect.support;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;

public abstract class AbstractConnection implements Connection{

	protected SocketChannel channel;
	protected SelectionKey key;
	protected NioDispatcherEventLoop eventLoop;
	
	protected AbstractConnection(SocketChannel channel,NioDispatcherEventLoop eventLoop){
		this.channel = channel;
		this.eventLoop = eventLoop;
	}

	@Override
	public boolean isOpen(){
		return null != this.channel && this.channel.isOpen();
	}
	
	@Override
	public SocketChannel getChannel(){
		return this.channel;
	}

	@Override
	public void postRegist(Selector selector) throws IOException{
		this.channel.configureBlocking(false);
		this.key = this.channel.register(selector,SelectionKey.OP_READ,this);
		this.key.selector().wakeup();
	}
	
	@Override
	public void postConnector(Selector selector) throws IOException{
		this.channel.finishConnect();
		selector.wakeup();
	}

	@Override
	public void close(){
		this.close(null);
	}

	@Override
	public NioDispatcherEventLoop getEventLoop(){
		return this.eventLoop;
	}
	
}