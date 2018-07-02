package com.jcute.core.network.net.support;

import com.jcute.core.network.NetWorkManager;
import com.jcute.core.network.net.NetClient;
import com.jcute.core.network.net.NetClientEvent;
import com.jcute.core.network.net.NetClientHandlerFactory;
import com.jcute.core.network.net.NetClientListener;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.toolkit.cycle.support.AbstractStable;

public abstract class AbstractNetClient extends AbstractStable<NetClientEvent,NetClientListener> implements NetClient{

	protected NetWorkManager netWorkManager;
	protected NetClientOptions netClientOptions;

	public AbstractNetClient(NetWorkManager netWorkManager,NetClientOptions netClientOptions){
		if(null == netWorkManager){
			throw new IllegalArgumentException("net work manager must not be null");
		}
		if(null == netClientOptions){
			throw new IllegalArgumentException("net client options must not be null");
		}
		this.netWorkManager = netWorkManager;
		this.netClientOptions = netClientOptions;
	}

	@Override
	public NetWorkManager getNetWorkManager(){
		return this.netWorkManager;
	}

	@Override
	public NetClientOptions getNetClientOptions(){
		return this.netClientOptions;
	}

	@Override
	public NetClientHandlerFactory getHandlerFactory(){
		return this.netClientOptions.getHandlerFactory();
	}

	@Override
	protected NetClientEvent createEvent(){
		return new NetClientEvent() {
			@Override
			public NetClient getNetClient(){
				return AbstractNetClient.this;
			}

			@Override
			public NetWorkManager getNetWorkManager(){
				return netWorkManager;
			}
		};
	}

}