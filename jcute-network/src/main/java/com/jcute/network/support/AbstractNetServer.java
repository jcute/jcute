package com.jcute.network.support;

import com.jcute.network.NetServer;
import com.jcute.network.NetServerInitializer;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopGroup;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopGroup;
import com.jcute.network.toolkit.NetAddress;
import com.jcute.network.toolkit.execute.support.AbstractExecutable;

public abstract class AbstractNetServer extends AbstractExecutable implements NetServer{

	protected NetAddress bindAddress;
	protected NetServerInitializer netServerInitializer;
	protected NioAcceptorEventLoopGroup nioAcceptorEventLoopGroup;
	protected NioDispatcherEventLoopGroup nioDispatcherEventLoopGroup;

	protected AbstractNetServer(NetAddress bindAddress,NetServerInitializer netServerInitializer){
		this.bindAddress = bindAddress;
		this.netServerInitializer = netServerInitializer;
	}

	@Override
	public NetAddress getBindAddress(){
		return this.nioAcceptorEventLoopGroup.getNextEventLoop().getBindAddress();
	}

	@Override
	public NetServerInitializer getNetServerInitializer(){
		return this.netServerInitializer;
	}
	
	@Override
	protected void doStart() throws Exception{
		this.nioAcceptorEventLoopGroup = this.doCreateNioAcceptorEventLoopGroup(this,this.bindAddress);
		this.nioDispatcherEventLoopGroup = this.doCreateNioDispatcherEventLoopGroup(this);
		this.nioAcceptorEventLoopGroup.start();
		this.nioDispatcherEventLoopGroup.start();
	}

	@Override
	protected void doClose() throws Exception{
		if(null != this.nioAcceptorEventLoopGroup){
			this.nioAcceptorEventLoopGroup.close();
		}
		if(null != this.nioDispatcherEventLoopGroup){
			this.nioDispatcherEventLoopGroup.close();
		}
	}

	protected abstract NioAcceptorEventLoopGroup doCreateNioAcceptorEventLoopGroup(NetServer netServer,NetAddress bindAddress);

	protected abstract NioDispatcherEventLoopGroup doCreateNioDispatcherEventLoopGroup(NetServer netServer);

}