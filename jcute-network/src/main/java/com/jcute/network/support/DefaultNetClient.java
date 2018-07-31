package com.jcute.network.support;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.network.NetClient;
import com.jcute.network.NetClientInitializer;
import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.connect.support.DefaultConnection;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopChain;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopGroup;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopHandlerInitializer;
import com.jcute.network.reactor.dispacth.support.DefaultNioDispatcherEventLoopGroup;
import com.jcute.network.reactor.dispacth.support.DefaultNioDispatcherEventLoopHandler;
import com.jcute.network.toolkit.NetAddress;

public class DefaultNetClient extends AbstractNetClient implements Runnable, NioDispatcherEventLoopHandlerInitializer{

	private static final Logger logger = LoggerFactory.getLogger(DefaultNetClient.class);

	private NetAddress connectAddress;
	private NetAddress bindAddress;

	private Selector selector;
	private SocketChannel socketChannel;

	private Connection connection;
	private Thread thread;

	private CountDownLatch latch = new CountDownLatch(1);

	public DefaultNetClient(NetAddress connectAddress,NetClientInitializer initializer){
		super(initializer);
		this.connectAddress = connectAddress;
	}

	@Override
	public NetAddress getBindAddress(){
		return this.bindAddress;
	}

	@Override
	public NetAddress getConnectAddress(){
		return this.connectAddress;
	}

	@Override
	protected void doStart() throws Exception{
		super.doStart();
		this.thread = new Thread(this,"connector");
		this.selector = Selector.open();
		this.socketChannel = SocketChannel.open();
		this.socketChannel.configureBlocking(false);
		this.socketChannel.register(this.selector,SelectionKey.OP_CONNECT);
		this.socketChannel.connect(this.connectAddress.toSocketAddress());
		this.thread.start();
		latch.await();
	}

	@Override
	protected void doClose() throws Exception{
		super.doClose();
		if(null != this.connection){
			this.connection.close();
		}
		if(null != this.selector){
			try{
				this.selector.close();
			}catch(Throwable t){
				logger.error(t.getMessage(),t);
			}
		}
		if(null != this.socketChannel){
			try{
				this.socketChannel.close();
				this.socketChannel.socket().close();
			}catch(Throwable t){
				logger.error(t.getMessage(),t);
			}
		}
	}

	@Override
	protected NioDispatcherEventLoopGroup doCreateNioDispatcherEventLoopGroup(NetClient netClient){
		return new DefaultNioDispatcherEventLoopGroup(this,1);
	}

	@Override
	public Connection getConnection(){
		return this.connection;
	}

	@Override
	public void run(){
		while(this.isRunning()){
			try{
				int size = this.selector.select(2000L);
				if(size != 0){
					Iterator<SelectionKey> iter = this.selector.selectedKeys().iterator();
					while(iter.hasNext()){
						try{
							SelectionKey key = iter.next();
							if(key.isValid() && key.isConnectable()){
								NioDispatcherEventLoop eventLoop = this.nioDispatcherEventLoopGroup.getNextEventLoop();
								this.connection = new DefaultConnection(false,this.socketChannel,eventLoop,this.netClientInitializer.createHandler());
								eventLoop.postConnect(this.connection,this.selector);
								latch.countDown();
								key.interestOps(SelectionKey.OP_READ);
//								this.connectAddress = NetAddress.create((InetSocketAddress)this.socketChannel.socket().getRemoteSocketAddress());
//								this.bindAddress = NetAddress.create((InetSocketAddress)this.socketChannel.socket().getLocalSocketAddress());
							}else{
								key.cancel();
							}
						}finally{
							iter.remove();
						}
					}
				}
			}catch(IOException e){
				try{
					this.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void initHandler(NioDispatcherEventLoopChain chain){
		chain.attachFirst("to-operation",new DefaultNioDispatcherEventLoopHandler() {
			@Override
			public boolean onConnect(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector){
				try{
					connection.postConnector(selector);
					return true;
				}catch(IOException e){
					connection.close(e);
					return false;
				}
			}

			@Override
			public boolean onRegist(NioDispatcherEventLoop eventLoop,Connection connection,Selector selector){
				try{
					connection.postRegist(selector);
					return true;
				}catch(IOException e){
					connection.close(e);
					return false;
				}
			}

			@Override
			public boolean onReader(NioDispatcherEventLoop eventLoop,Connection connection){
				connection.postReader();
				return true;
			}

			@Override
			public boolean onWriter(NioDispatcherEventLoop eventLoop,Connection connection){
				connection.postWriter();
				return true;
			}

			@Override
			public boolean onCaught(NioDispatcherEventLoop eventLoop,Connection connection,Throwable cause){
				connection.close(cause);
				return false;
			}

		});
	}

	@Override
	public PacketEncoder getPacketEncoder(){
		return this.netClientInitializer.createPacketEncoder();
	}

	@Override
	public PacketDecoder getPacketDecoder(){
		return this.netClientInitializer.createPacketDecoder();
	}

}