package com.jcute.core.network.net.support;

import com.jcute.core.network.NetWorkManager;
import com.jcute.core.network.net.NetServer;
import com.jcute.core.network.net.NetServerEvent;
import com.jcute.core.network.net.NetServerHandlerFactory;
import com.jcute.core.network.net.NetServerListener;
import com.jcute.core.network.net.NetServerOptions;
import com.jcute.core.toolkit.cycle.support.AbstractStable;

public abstract class AbstractNetServer extends AbstractStable<NetServerEvent,NetServerListener> implements NetServer{

	protected NetWorkManager netWorkManager;
	protected NetServerOptions netServerOptions;

	public AbstractNetServer(NetWorkManager netWorkManager,NetServerOptions netServerOptions){
		if(null == netWorkManager){
			throw new IllegalArgumentException("net work manager must not be null");
		}
		if(null == netServerOptions){
			throw new IllegalArgumentException("net server options must not be null");
		}
		this.netWorkManager = netWorkManager;
		this.netServerOptions = netServerOptions;
	}

	@Override
	public NetWorkManager getNetWorkManager(){
		return this.netWorkManager;
	}

	@Override
	public NetServerOptions getNetServerOptions(){
		return this.netServerOptions;
	}

	@Override
	public NetServerHandlerFactory getHandlerFactory(){
		return this.netServerOptions.getHandlerFactory();
	}

	@Override
	protected NetServerEvent createEvent(){
		return new NetServerEvent() {
			@Override
			public NetWorkManager getNetWorkManager(){
				return netWorkManager;
			}

			@Override
			public NetServer getNetServer(){
				return AbstractNetServer.this;
			}
		};
	}
	
}