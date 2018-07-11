package com.jcute.core.net.nio.support;

import com.jcute.core.net.looper.EventLoopGroup;
import com.jcute.core.net.looper.support.AbstractEventLoop;
import com.jcute.core.util.GenericUtils;

public class NioEventLoop extends AbstractEventLoop{

	public NioEventLoop(EventLoopGroup eventLoopGroup){
		super(eventLoopGroup);
	}

	@Override
	public NioEventLoopGroup getEventLoopGroup(){
		return GenericUtils.parse(super.getEventLoopGroup());
	}

	@Override
	protected void doLooper() throws Exception{
		
	}
	
}