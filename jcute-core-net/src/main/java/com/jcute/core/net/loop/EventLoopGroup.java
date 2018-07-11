package com.jcute.core.net.loop;

import com.jcute.core.toolkit.cycle.Stable;
import com.jcute.core.toolkit.cycle.StableEvent;

public interface EventLoopGroup extends StableEvent,Stable<EventLoopGroup,EventLoopGroupListener>{
	
	public EventLoop getEventLoop(int index);
	
	public EventLoop getNextEventLoop();
	
}