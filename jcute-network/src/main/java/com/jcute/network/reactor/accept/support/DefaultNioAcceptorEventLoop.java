package com.jcute.network.reactor.accept.support;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.network.reactor.accept.NioAcceptorEventLoop;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopChain;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopGroup;
import com.jcute.network.toolkit.NetAddress;
import com.jcute.network.toolkit.looper.support.AbstractEventLoop;

public class DefaultNioAcceptorEventLoop extends AbstractEventLoop implements NioAcceptorEventLoop{

	private static final Logger logger = LoggerFactory.getLogger(DefaultNioAcceptorEventLoop.class);
	private static final long TIMEOUT = 5000;

	private NetAddress bindAddress;
	private NioAcceptorEventLoopChain nioAcceptorEventLoopChain;

	private Selector selector;
	private ServerSocketChannel serverChannel;

	public DefaultNioAcceptorEventLoop(NioAcceptorEventLoopGroup eventLoopGroup,String eventLoopName,NetAddress bindAddress){
		super(eventLoopGroup,eventLoopName);
		this.bindAddress = bindAddress;
		this.nioAcceptorEventLoopChain = new DefaultNioAcceptorEventLoopChain(this);
	}

	@Override
	public NioAcceptorEventLoopGroup getEventLoopGroup(){
		return (NioAcceptorEventLoopGroup)super.getEventLoopGroup();
	}

	@Override
	public NioAcceptorEventLoopChain getHandlerChain(){
		return this.nioAcceptorEventLoopChain;
	}

	@Override
	protected void doLoop() throws Exception{
		int count = this.selector.select(TIMEOUT);
		if(count > 0){
			this.processSelect();
		}
	}

	@Override
	protected void onStart() throws Exception{
		this.selector = Selector.open();
		this.serverChannel = ServerSocketChannel.open();
		this.serverChannel.configureBlocking(false);
		this.serverChannel.register(this.selector,SelectionKey.OP_ACCEPT);
		this.nioAcceptorEventLoopChain.fireOnAccept(this.serverChannel);
		this.serverChannel.socket().bind(this.bindAddress.toSocketAddress());
		this.bindAddress = NetAddress.create((InetSocketAddress)this.serverChannel.socket().getLocalSocketAddress());
	}

	@Override
	protected void onClose() throws Exception{
		if(null != this.selector){
			try{
				this.selector.close();
			}catch(Throwable t){
				logger.error("selector close error {}",t.getMessage(),t);
			}
		}
		if(null != this.serverChannel){
			try{
				this.serverChannel.close();
				this.serverChannel.socket().close();
			}catch(Throwable t){
				logger.error("channel close error {}",t.getMessage(),t);
			}
		}
		this.bindAddress = null;
		this.nioAcceptorEventLoopChain = null;
		this.selector = null;
		this.serverChannel = null;
	}

	@Override
	public NetAddress getBindAddress(){
		return this.bindAddress;
	}

	private void processSelect(){
		Iterator<SelectionKey> iter = this.selector.selectedKeys().iterator();
		while(iter.hasNext()){
			try{
				SelectionKey key = iter.next();
				int readyOps = key.readyOps();
				if((readyOps & SelectionKey.OP_ACCEPT) != 0){
					this.doProcessSelect(key);
				}else{
					key.cancel();
				}
			}finally{
				iter.remove();
			}
		}
	}

	private void doProcessSelect(SelectionKey key){
		try{
			SocketChannel clientChannel = this.serverChannel.accept();
			this.nioAcceptorEventLoopChain.fireOnSelect(this.serverChannel,clientChannel);
		}catch(IOException e){
			key.cancel();
			logger.error(e.getMessage(),e);
		}
	}

}