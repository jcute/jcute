package com.jcute.core.toolkit.cycle;

import com.jcute.core.toolkit.cycle.support.AbstractStable;

public class Application extends AbstractStable<StableEvent,StableListener<StableEvent>>{
	
	@Override
	protected void doStart() throws Exception{
		System.out.println("start success from application");
	}

	@Override
	protected void doClose() throws Exception{
		System.out.println("close success from application");
	}

	@Override
	protected StableEvent createEvent(){
		return new StableEvent() {};
	}

}