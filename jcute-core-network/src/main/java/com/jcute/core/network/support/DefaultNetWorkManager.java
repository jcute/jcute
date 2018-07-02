package com.jcute.core.network.support;

import com.jcute.core.network.NetWorkManagerOptions;
import com.jcute.core.network.net.NetClient;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.network.net.NetServer;
import com.jcute.core.network.net.NetServerOptions;
import com.jcute.core.network.net.support.DefaultNetClient;
import com.jcute.core.network.net.support.DefaultNetClientOptions;
import com.jcute.core.network.net.support.DefaultNetServer;
import com.jcute.core.network.net.support.DefaultNetServerOptions;

public class DefaultNetWorkManager extends AbstractNetWorkManager{

	public DefaultNetWorkManager(NetWorkManagerOptions netWorkManagerOptions){
		super(netWorkManagerOptions);
	}
	
	public DefaultNetWorkManager(){
		super(null);
	}
	
	@Override
	protected NetWorkManagerOptions doCreateNetWorkManagerOptions(){
		return new DefaultNetWorkManagerOptions();
	}
	
	@Override
	protected NetServerOptions doCreateNetServerOptions(){
		return new DefaultNetServerOptions();
	}
	
	@Override
	protected NetServer doCreateNetServer(NetServerOptions netServerOptions){
		return new DefaultNetServer(this,netServerOptions);
	}

	@Override
	protected NetClientOptions doCreateNetClientOptions(){
		return new DefaultNetClientOptions();
	}

	@Override
	protected NetClient doCreateNetClient(NetClientOptions netClientOptions){
		return new DefaultNetClient(this,netClientOptions);
	}
	
}