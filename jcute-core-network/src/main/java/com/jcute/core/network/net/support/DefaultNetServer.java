package com.jcute.core.network.net.support;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.NetWorkManager;
import com.jcute.core.network.net.NetServerOptions;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public class DefaultNetServer extends AbstractNetServer{

	private static final Logger logger = LoggerFactory.getLogger(DefaultNetServer.class);

	private Channel channel;
	private NetWorkAddress address;

	public DefaultNetServer(NetWorkManager netWorkManager,NetServerOptions netServerOptions){
		super(netWorkManager,netServerOptions);
	}

	@Override
	public NetWorkAddress getBindNetWorkAddress(){
		return this.address;
	}

	@Override
	protected void doStart() throws Exception{
		final CountDownLatch latch = new CountDownLatch(1);
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.group(this.netWorkManager.getBossEventLoopGroup(),this.netWorkManager.getWorkEventLoopGroup());
		serverBootstrap.option(ChannelOption.SO_BACKLOG,this.netServerOptions.getAcceptBackLog());
		serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception{
				ChannelPipeline channelPipeline = socketChannel.pipeline();
				channelPipeline.addLast(new DefaultNetServerDecoder(getNetServerOptions().getNetServerDecoder()));
				channelPipeline.addLast(new DefaultNetServerEncoder(getNetServerOptions().getNetServerEncoder()));
				channelPipeline.addLast(new DefaultNetServerHandler(getNetServerOptions().getNetServerHandler()));
			}
		});
		ChannelFuture channelFuture = serverBootstrap.bind(this.getNetServerOptions().getNetWorkAddress().getInetSocketAddress());
		channelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception{
				if(future.isSuccess()){
					channel = future.channel();
					address = NetWorkAddress.create((InetSocketAddress)channel.localAddress());
					logger.debug("net server start success {}",address);
				}
				latch.countDown();
			}
		});
		latch.await();
	}

	@Override
	protected void doClose() throws Exception{
		final CountDownLatch latch = new CountDownLatch(1);
		if(null != channel){
			channel.close().addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception{
					latch.countDown();
				}
			});
		}
		latch.await();
	}

}