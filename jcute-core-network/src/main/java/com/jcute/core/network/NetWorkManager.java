package com.jcute.core.network;

import io.netty.channel.EventLoopGroup;

import java.util.Set;

import com.jcute.core.network.net.NetClient;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.network.net.NetServer;
import com.jcute.core.network.net.NetServerOptions;
import com.jcute.core.network.web.WebClient;
import com.jcute.core.network.web.WebClientOptions;
import com.jcute.core.network.web.WebServer;
import com.jcute.core.network.web.WebServerOptions;
import com.jcute.core.toolkit.cycle.Stable;

public interface NetWorkManager extends Stable<NetWorkManagerEvent,NetWorkManagerListener>{
	
	public NetWorkManagerOptions getNetWorkManagerOptions();
	
	public NetWorkThreadChecker getNetWorkThreadChecker();
	
	public NetWorkThreadFactory getBossNetWorkThreadFactory();
	
	public NetWorkThreadFactory getWorkNetWorkThreadFactory();
	
	public EventLoopGroup getBossEventLoopGroup();
	
	public EventLoopGroup getWorkEventLoopGroup();
	
	public NetServer createNetServer(NetServerOptions options);
	
	public NetServer createNetServer();
	
	public NetClient createNetClient(NetClientOptions options);
	
	public NetClient createNetClient();
	
	public WebServer createWebServer(WebServerOptions options);
	
	public WebServer createWebServer();
	
	public WebClient createWebClient(WebClientOptions options);
	
	public WebClient createWebClient();
	
	public Set<NetServer> getNetServers();
	
	public Set<NetClient> getNetClients();
	
	public Set<WebServer> getWebServers();
	
	public Set<WebClient> getWebClients();
	
}