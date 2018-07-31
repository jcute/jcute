package com.jcute.network.support;

import com.jcute.network.NetClient;
import com.jcute.network.NetClientInitializer;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopGroup;
import com.jcute.network.toolkit.execute.support.AbstractExecutable;

public abstract class AbstractNetClient extends AbstractExecutable implements NetClient{
	
	protected NetClientInitializer netClientInitializer;
	protected NioDispatcherEventLoopGroup nioDispatcherEventLoopGroup;
	
	protected AbstractNetClient(NetClientInitializer initializer){
		this.netClientInitializer = initializer;
	}
	
	@Override
	public NetClientInitializer getNetClientInitializer(){
		return this.netClientInitializer;
	}

	@Override
	protected void doStart() throws Exception{
		this.nioDispatcherEventLoopGroup = this.doCreateNioDispatcherEventLoopGroup(this);
		this.nioDispatcherEventLoopGroup.start();
	}

	@Override
	protected void doClose() throws Exception{
		if(null != this.nioDispatcherEventLoopGroup){
			this.nioDispatcherEventLoopGroup.close();
		}
	}
	
	protected abstract NioDispatcherEventLoopGroup doCreateNioDispatcherEventLoopGroup(NetClient netClient);
	
}