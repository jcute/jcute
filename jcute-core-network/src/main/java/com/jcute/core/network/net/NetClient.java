package com.jcute.core.network.net;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.NetWorkManager;

public interface NetClient{

	public NetWorkManager getNetWorkManager();

	public NetClientOptions getNetClientOptions();

	public NetClientHandlerFactory getHandlerFactory();

	public NetWorkAddress getBindNetWorkAddress();

}