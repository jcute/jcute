package com.jcute.network.support;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.jcute.network.NetServer;
import com.jcute.network.NetServerInitializer;
import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.accept.NioAcceptorEventLoop;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopChain;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopGroup;
import com.jcute.network.reactor.accept.NioAcceptorEventLoopHandlerInitializer;
import com.jcute.network.reactor.accept.support.DefaultNioAcceptorEventLoopGroup;
import com.jcute.network.reactor.accept.support.DefaultNioAcceptorEventLoopHandler;
import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.connect.support.DefaultConnection;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopChain;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopGroup;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopHandlerInitializer;
import com.jcute.network.reactor.dispacth.support.DefaultNioDispatcherEventLoopGroup;
import com.jcute.network.reactor.dispacth.support.DefaultNioDispatcherEventLoopHandler;
import com.jcute.network.toolkit.NetAddress;

public class DefaultNetServer extends AbstractNetServer implements NioAcceptorEventLoopHandlerInitializer, NioDispatcherEventLoopHandlerInitializer{

	public DefaultNetServer(NetAddress bindAddress,NetServerInitializer netServerInitializer){
		super(bindAddress,netServerInitializer);
	}

	@Override
	protected NioAcceptorEventLoopGroup doCreateNioAcceptorEventLoopGroup(NetServer netServer,NetAddress bindAddress){
		return new DefaultNioAcceptorEventLoopGroup(this,bindAddress);
	}

	@Override
	protected NioDispatcherEventLoopGroup doCreateNioDispatcherEventLoopGroup(NetServer netServer){
		return new DefaultNioDispatcherEventLoopGroup(this);
	}

	@Override
	public void initHandler(NioAcceptorEventLoopChain chain){
		chain.attachLast("to-dispatcher",new DefaultNioAcceptorEventLoopHandler() {

			@Override
			public boolean onSelect(NioAcceptorEventLoop eventLoop,ServerSocketChannel serverChannel,SocketChannel clientChannel){
				NioDispatcherEventLoop nioDispatcherEventLoop = nioDispatcherEventLoopGroup.getNextEventLoop();
				Connection connection = new DefaultConnection(true,clientChannel,nioDispatcherEventLoop,netServerInitializer.createHandler());
				nioDispatcherEventLoop.postRegist(connection);
				return true;
			}

		});
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
		return netServerInitializer.createPacketEncoder();
	}

	@Override
	public PacketDecoder getPacketDecoder(){
		return netServerInitializer.createPacketDecoder();
	}

}