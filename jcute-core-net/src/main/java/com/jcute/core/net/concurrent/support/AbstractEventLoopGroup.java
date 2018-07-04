package com.jcute.core.net.concurrent.support;

import com.jcute.core.net.concurrent.EventLoop;
import com.jcute.core.net.concurrent.EventLoopGroup;
import com.jcute.core.net.concurrent.EventLoopGroupListener;
import com.jcute.core.net.toolkit.FixedAtomicInteger;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public abstract class AbstractEventLoopGroup extends AbstractStable<EventLoopGroup,EventLoopGroupListener> implements EventLoopGroup{

	private static final Logger logger = LoggerFactory.getLogger(AbstractEventLoopGroup.class);

	protected String eventLoopName;
	protected int eventLoopSize;
	protected FixedAtomicInteger eventLoopIndex;

	protected AbstractEventLoopGroup(String eventLoopName,int eventLoopSize){
		this.eventLoopName = eventLoopName;
		this.eventLoopSize = eventLoopSize;
	}

	@Override
	protected EventLoopGroup createEvent(){
		return this;
	}

	@Override
	protected void doStart() throws Exception{
		this.eventLoopIndex = new FixedAtomicInteger(0,this.eventLoopSize - 1);
		EventLoop[] eventLoops = new EventLoop[this.eventLoopSize];
		for(int i = 0;i < eventLoops.length;i++){
			eventLoops[i] = this.createEventLoop(i);
		}
		for(int i = 0;i < eventLoops.length;i++){
			eventLoops[i].start();
		}
	}
	
	@Override
	protected void doClose() throws Exception{
		for(int i = 0;i < this.eventLoopSize;i++){
			try{
				this.getEventLoop(i).close();
			}catch(Exception e){
				logger.warn("close event loop failed {}",e.getMessage(),e);
			}
		}
	}

	protected abstract EventLoop createEventLoop(int index);

}