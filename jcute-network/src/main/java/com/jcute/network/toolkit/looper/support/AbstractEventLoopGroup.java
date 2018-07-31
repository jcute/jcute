package com.jcute.network.toolkit.looper.support;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.network.toolkit.FixedAtomicInteger;
import com.jcute.network.toolkit.execute.support.AbstractExecutable;
import com.jcute.network.toolkit.looper.EventLoop;
import com.jcute.network.toolkit.looper.EventLoopGroup;

public abstract class AbstractEventLoopGroup extends AbstractExecutable implements EventLoopGroup{

	private static final Logger logger = LoggerFactory.getLogger(AbstractEventLoopGroup.class);

	private String eventLoopName;
	private int eventLoopSize;
	private EventLoop[] eventLoopPool;
	private FixedAtomicInteger eventLoopIndex;

	protected AbstractEventLoopGroup(String eventLoopName,int eventLoopSize){
		this.eventLoopName = eventLoopName;
		this.eventLoopSize = eventLoopSize;
	}

	@Override
	public int getEventLoopSize(){
		return this.eventLoopPool.length;
	}

	@Override
	public EventLoop getEventLoop(int index){
		if(index < 0 || index >= this.eventLoopPool.length){
			return null;
		}
		return this.eventLoopPool[index];
	}

	@Override
	public EventLoop getNextEventLoop(){
		return this.eventLoopPool[this.eventLoopIndex.getAndIncrement()];
	}

	@Override
	protected final void doStart() throws Exception{
		this.eventLoopIndex = new FixedAtomicInteger(0,this.eventLoopSize - 1);
		this.eventLoopPool = new EventLoop[this.eventLoopSize];
		for(int i = 0;i < this.eventLoopPool.length;i++){
			this.eventLoopPool[i] = this.doCreateEventLoop(String.format("%s-%d",this.eventLoopName,i + 1));
		}
		for(int i = 0;i < this.eventLoopPool.length;i++){
			this.eventLoopPool[i].start();
		}
		this.onStart();
	}

	@Override
	protected final void doClose() throws Exception{
		this.onClose();
		for(int i = 0;i < this.eventLoopPool.length;i++){
			try{
				this.eventLoopPool[i].close();
			}catch(Throwable t){
				logger.error("close {} error {}",this.getClass().getSimpleName(),t.getMessage(),t);
			}
		}
		this.eventLoopIndex = null;
		this.eventLoopName = null;
		this.eventLoopPool = null;
		this.eventLoopSize = 0;
	}

	protected abstract EventLoop doCreateEventLoop(String eventLoopName);

	protected abstract void onStart() throws Exception;

	protected abstract void onClose() throws Exception;

}