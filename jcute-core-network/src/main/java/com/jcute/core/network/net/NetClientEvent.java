package com.jcute.core.network.net;

import com.jcute.core.network.NetWorkManager;
import com.jcute.core.toolkit.cycle.StableEvent;

public interface NetClientEvent extends StableEvent{

	public NetClient getNetClient();

	public NetWorkManager getNetWorkManager();

}