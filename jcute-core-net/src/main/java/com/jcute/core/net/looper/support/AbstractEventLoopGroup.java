package com.jcute.core.net.looper.support;

import com.jcute.core.net.looper.EventLoop;
import com.jcute.core.net.looper.EventLoopGroup;
import com.jcute.core.net.looper.EventLoopGroupListener;
import com.jcute.core.net.toolkit.FixedAtomicInteger;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public abstract class AbstractEventLoopGroup extends AbstractStable<EventLoopGroup,EventLoopGroupListener> implements EventLoopGroup{

	private static final Logger logger = LoggerFactory.getLogger(AbstractEventLoopGroup.class);

	private int eventLoopSize;
	private String eventLoopName;
	private FixedAtomicInteger eventLoopIndex;
	private EventLoop[] eventLoops;

	public AbstractEventLoopGroup(String eventLoopName,int eventLoopSize){
		this.eventLoopName = eventLoopName;
		this.eventLoopSize = eventLoopSize;
	}

	@Override
	public EventLoop getEventLoop(int index){
		if(index >= 0 && index < this.eventLoops.length){
			return this.eventLoops[index];
		}
		return null;
	}

	@Override
	public EventLoop getNextEventLoop(){
		return this.getEventLoop(this.eventLoopIndex.getAndIncrement());
	}

	@Override
	protected EventLoopGroup createEvent(){
		return this;
	}

	@Override
	protected void doStart() throws Exception{
		this.eventLoopIndex = new FixedAtomicInteger(0,this.eventLoopSize - 1);
		this.eventLoops = new EventLoop[this.eventLoopSize];
		for(int i = 0;i < this.eventLoops.length;i++){
			this.eventLoops[i] = this.createEventLoop(i);
		}
		for(int i = 0;i < this.eventLoops.length;i++){
			this.eventLoops[i].start(String.format("%s-%d",this.eventLoopName,i + 1));
		}
	}

	@Override
	protected void doClose() throws Exception{
		for(int i = 0;i < this.eventLoops.length;i++){
			try{
				this.eventLoops[i].close();
			}catch(Exception e){
				logger.error("close event loop failed {}",e.getMessage(),e);
			}
		}
	}

	protected abstract EventLoop createEventLoop(int index);

}