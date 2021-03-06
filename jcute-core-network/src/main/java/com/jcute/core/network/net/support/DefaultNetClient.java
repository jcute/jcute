package com.jcute.core.network.net.support;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

import com.jcute.core.network.NetWorkAddress;
import com.jcute.core.network.NetWorkManager;
import com.jcute.core.network.net.NetClientOptions;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public class DefaultNetClient extends AbstractNetClient{

	private static final Logger logger = LoggerFactory.getLogger(DefaultNetClient.class);

	private Channel channel;
	private NetWorkAddress address;

	public DefaultNetClient(NetWorkManager netWorkManager,NetClientOptions netClientOptions){
		super(netWorkManager,netClientOptions);
	}

	@Override
	public NetWorkAddress getBindNetWorkAddress(){
		return this.address;
	}

	@Override
	protected void doStart() throws Exception{
		final CountDownLatch latch = new CountDownLatch(1);
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(this.netWorkManager.getWorkEventLoopGroup());
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception{
				ChannelPipeline channelPipeline = socketChannel.pipeline();
				channelPipeline.addLast(new DefaultNetClientEncoder(getNetClientOptions().getNetClientEncoder()));
				channelPipeline.addLast(new DefaultNetClientDecoder(getNetClientOptions().getNetClientDecoder()));
				channelPipeline.addLast(new DefaultNetClientHandler(getNetClientOptions().getNetClientHandler()));
			}
		});
		ChannelFuture channelFuture = bootstrap.connect(this.getNetClientOptions().getNetWorkAddress().getInetSocketAddress());
		channelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception{
				if(future.isSuccess()){
					channel = future.channel();
					address = NetWorkAddress.create((InetSocketAddress)channel.localAddress());
					logger.debug("net client start success {}",address);
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