package com.jcute.core.network.net;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.NetWorkManager;
import com.jcute.core.toolkit.cycle.Stable;

public interface NetClient extends Stable<NetClientEvent,NetClientListener>{

	public NetWorkManager getNetWorkManager();

	public NetClientOptions getNetClientOptions();

	public NetClientHandlerFactory getHandlerFactory();

	public NetWorkAddress getBindNetWorkAddress();

}