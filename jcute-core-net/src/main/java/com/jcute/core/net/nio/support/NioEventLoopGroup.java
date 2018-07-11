package com.jcute.core.net.nio.support;

import com.jcute.core.net.looper.support.AbstractEventLoopGroup;
import com.jcute.core.util.GenericUtils;

public class NioEventLoopGroup extends AbstractEventLoopGroup{

	public NioEventLoopGroup(String eventLoopName,int eventLoopSize){
		super(eventLoopName,eventLoopSize);
	}
	
	@Override
	public NioEventLoop getEventLoop(int index){
		return GenericUtils.parse(super.getEventLoop(index));
	}

	@Override
	public NioEventLoop getNextEventLoop(){
		return GenericUtils.parse(super.getNextEventLoop());
	}
	
	@Override
	protected NioEventLoop createEventLoop(int index){
		return new NioEventLoop(this);
	}
	
}