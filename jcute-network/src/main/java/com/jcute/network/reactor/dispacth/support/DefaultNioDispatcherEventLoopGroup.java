package com.jcute.network.reactor.dispacth.support;

import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopChain;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopGroup;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopHandlerInitializer;
import com.jcute.network.toolkit.looper.EventLoop;
import com.jcute.network.toolkit.looper.support.AbstractEventLoopGroup;

public class DefaultNioDispatcherEventLoopGroup extends AbstractEventLoopGroup implements NioDispatcherEventLoopGroup, NioDispatcherEventLoopHandlerInitializer{

	private static final String NAME = "dispatcher";
	private static final int SIZE = Runtime.getRuntime().availableProcessors();

	private NioDispatcherEventLoopHandlerInitializer initializer;

	public DefaultNioDispatcherEventLoopGroup(NioDispatcherEventLoopHandlerInitializer initializer,int eventLoopSize){
		super(NAME,eventLoopSize);
		if(null == initializer){
			this.initializer = this;
		}else{
			this.initializer = initializer;
		}
	}

	public DefaultNioDispatcherEventLoopGroup(int eventLoopSize){
		this(null,eventLoopSize);
	}

	public DefaultNioDispatcherEventLoopGroup(NioDispatcherEventLoopHandlerInitializer initializer){
		this(initializer,SIZE);
	}

	public DefaultNioDispatcherEventLoopGroup(){
		this(null,SIZE);
	}

	@Override
	public NioDispatcherEventLoop getEventLoop(int index){
		return (NioDispatcherEventLoop)super.getEventLoop(index);
	}

	@Override
	public NioDispatcherEventLoop getNextEventLoop(){
		return (NioDispatcherEventLoop)super.getNextEventLoop();
	}

	@Override
	protected EventLoop doCreateEventLoop(String eventLoopName){
		return new DefaultNioDispatcherEventLoop(this,eventLoopName,this.initializer.getPacketDecoder(),this.initializer.getPacketEncoder());
	}

	@Override
	protected void onStart() throws Exception{
		for(int i = 0;i < this.getEventLoopSize();i++){
			this.initializer.initHandler(this.getEventLoop(i).getHandlerChain());
		}
	}

	@Override
	protected void onClose() throws Exception{

	}

	@Override
	public void initHandler(NioDispatcherEventLoopChain chain){

	}

	@Override
	public PacketEncoder getPacketEncoder(){
		return null;
	}

	@Override
	public PacketDecoder getPacketDecoder(){
		return null;
	}

}