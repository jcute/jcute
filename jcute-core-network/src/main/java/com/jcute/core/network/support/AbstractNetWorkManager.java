package com.jcute.core.network.support;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.internal.ConcurrentSet;

import java.util.Collections;
import java.util.Set;

import com.jcute.core.network.NetWorkManager;
import com.jcute.core.network.NetWorkManagerEvent;
import com.jcute.core.network.NetWorkManagerListener;
import com.jcute.core.network.NetWorkManagerOptions;
import com.jcute.core.network.NetWorkThreadChecker;
import com.jcute.core.network.NetWorkThreadFactory;
import com.jcute.core.network.net.NetClient;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.network.net.NetServer;
import com.jcute.core.network.net.NetServerEvent;
import com.jcute.core.network.net.NetServerListener;
import com.jcute.core.network.net.NetServerOptions;
import com.jcute.core.network.web.WebClient;
import com.jcute.core.network.web.WebClientOptions;
import com.jcute.core.network.web.WebServer;
import com.jcute.core.network.web.WebServerOptions;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public abstract class AbstractNetWorkManager extends AbstractStable<NetWorkManagerEvent,NetWorkManagerListener> implements NetWorkManager{

	private static final Logger logger = LoggerFactory.getLogger(AbstractNetWorkManager.class);

	protected NetWorkManagerOptions netWorkManagerOptions;
	protected NetWorkThreadChecker netWorkThreadChecker;
	protected NetWorkThreadFactory bossNetWorkThreadFactory;
	protected NetWorkThreadFactory workNetWorkThreadFacotry;
	protected EventLoopGroup bossEventLoopGroup;
	protected EventLoopGroup workEventLoopGroup;

	protected Set<NetServer> netServers = new ConcurrentSet<NetServer>();
	protected Set<NetClient> netClients = new ConcurrentSet<NetClient>();
	protected Set<WebServer> webServers = new ConcurrentSet<WebServer>();
	protected Set<WebClient> webClients = new ConcurrentSet<WebClient>();

	public AbstractNetWorkManager(NetWorkManagerOptions netWorkManagerOptions){
		if(null == netWorkManagerOptions){
			netWorkManagerOptions = this.doCreateNetWorkManagerOptions();
		}
		try{
			this.netWorkManagerOptions = netWorkManagerOptions;
			this.start();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public NetWorkManagerOptions getNetWorkManagerOptions(){
		return this.netWorkManagerOptions;
	}

	@Override
	public NetWorkThreadChecker getNetWorkThreadChecker(){
		return this.netWorkThreadChecker;
	}

	@Override
	public NetWorkThreadFactory getBossNetWorkThreadFactory(){
		return this.bossNetWorkThreadFactory;
	}

	@Override
	public NetWorkThreadFactory getWorkNetWorkThreadFactory(){
		return this.workNetWorkThreadFacotry;
	}

	@Override
	public EventLoopGroup getBossEventLoopGroup(){
		return this.bossEventLoopGroup;
	}

	@Override
	public EventLoopGroup getWorkEventLoopGroup(){
		return this.workEventLoopGroup;
	}

	@Override
	public NetServer createNetServer(){
		return this.createNetServer(null);
	}

	@Override
	public NetClient createNetClient(){
		return this.createNetClient(null);
	}

	@Override
	public WebServer createWebServer(){
		return this.createWebServer(null);
	}

	@Override
	public WebClient createWebClient(){
		return this.createWebClient(null);
	}

	@Override
	public Set<NetServer> getNetServers(){
		return Collections.unmodifiableSet(this.netServers);
	}

	@Override
	public Set<NetClient> getNetClients(){
		return Collections.unmodifiableSet(this.netClients);
	}

	@Override
	public Set<WebServer> getWebServers(){
		return Collections.unmodifiableSet(this.webServers);
	}

	@Override
	public Set<WebClient> getWebClients(){
		return Collections.unmodifiableSet(this.webClients);
	}

	@Override
	protected NetWorkManagerEvent createEvent(){
		return new NetWorkManagerEvent() {
			@Override
			public NetWorkManager getNetWorkManager(){
				return AbstractNetWorkManager.this;
			}
		};
	}

	@Override
	protected void doStart() throws Exception{
		this.netWorkThreadChecker = this.doCreateWorkThreadChecker();
		this.bossNetWorkThreadFactory = this.doCreateBossNetWorkThreadFactory();
		this.workNetWorkThreadFacotry = this.doCreateWorkNetWorkThreadFactory();
		this.bossEventLoopGroup = this.doCreateBossEventLoopGroup();
		this.workEventLoopGroup = this.doCreateWorkEventLoopGroup();
	}

	@Override
	protected void doClose() throws Exception{
		if(null != this.netServers && this.netServers.size() > 0){
			for(NetServer netServer : this.netServers){
				try{
					netServer.close();
					logger.debug("net server close success {}",netServer.getBindNetWorkAddress());
				}catch(Exception e){
					logger.warn("net server close failed {},{}",e.getMessage(),netServer);
				}
			}
		}
		if(null != this.netWorkThreadChecker){
			this.netWorkThreadChecker.close();
		}

		if(null != this.bossEventLoopGroup){
			this.bossEventLoopGroup.shutdownGracefully().sync();
		}
		if(null != this.workEventLoopGroup){
			this.workEventLoopGroup.shutdownGracefully().sync();
		}
	}

	@Override
	public NetServer createNetServer(NetServerOptions options){
		if(null == options){
			options = this.doCreateNetServerOptions();
		}
		NetServer netServer = this.doCreateNetServer(options);
		netServer.attachStartSuccessListener(new NetServerListener() {
			@Override
			public void execute(NetServerEvent event) throws Exception{
				netServers.add(event.getNetServer());
			}
		});
		netServer.attachCloseSuccessListener(new NetServerListener() {
			@Override
			public void execute(NetServerEvent event) throws Exception{
				netServers.remove(event.getNetServer());
			}
		});
		return netServer;
	}

	@Override
	public NetClient createNetClient(NetClientOptions options){
		return null;
	}

	@Override
	public WebServer createWebServer(WebServerOptions options){
		return null;
	}

	@Override
	public WebClient createWebClient(WebClientOptions options){
		return null;
	}

	protected NetWorkThreadFactory doCreateBossNetWorkThreadFactory(){
		return new NetWorkThreadFactory(this.netWorkManagerOptions.getBossThreadPrefix(),this.netWorkThreadChecker,false,this.netWorkManagerOptions.getMaxBossExecuteTime());
	}

	protected NetWorkThreadFactory doCreateWorkNetWorkThreadFactory(){
		return new NetWorkThreadFactory(this.netWorkManagerOptions.getWorkThreadPrefix(),this.netWorkThreadChecker,true,this.netWorkManagerOptions.getMaxWorkExecuteTime());
	}

	protected NetWorkThreadChecker doCreateWorkThreadChecker(){
		return new NetWorkThreadChecker(this.netWorkManagerOptions.getThreadCheckInterval(),this.netWorkManagerOptions.getWarningExceptionTime());
	}

	protected EventLoopGroup doCreateBossEventLoopGroup(){
		return new NioEventLoopGroup(this.netWorkManagerOptions.getBossPoolSize(),this.bossNetWorkThreadFactory);
	}

	protected EventLoopGroup doCreateWorkEventLoopGroup(){
		return new NioEventLoopGroup(this.netWorkManagerOptions.getWorkPoolSize(),this.workNetWorkThreadFacotry);
	}

	protected abstract NetWorkManagerOptions doCreateNetWorkManagerOptions();

	protected abstract NetServerOptions doCreateNetServerOptions();

	protected abstract NetServer doCreateNetServer(NetServerOptions netServerOptions);

}