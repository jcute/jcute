package com.jcute.network.reactor.dispacth.support;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.connect.Connection;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopChain;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoopGroup;
import com.jcute.network.toolkit.looper.EventLoopGroup;
import com.jcute.network.toolkit.looper.support.AbstractEventLoop;

public class DefaultNioDispatcherEventLoop extends AbstractEventLoop implements NioDispatcherEventLoop{

	private static final long TIMEOUT = 5000;

	private Selector selector;
	private Queue<Connection> registQueue;
	private Queue<ConnectTask> connectQueue;
	private NioDispatcherEventLoopChain handlerChain;

	private PacketDecoder packetDecoder;
	private PacketEncoder packetEncoder;

	public DefaultNioDispatcherEventLoop(EventLoopGroup eventLoopGroup,String eventLoopName,PacketDecoder packetDecoder,PacketEncoder packetEncoder){
		super(eventLoopGroup,eventLoopName);
		this.registQueue = new ConcurrentLinkedQueue<Connection>();
		this.connectQueue = new ConcurrentLinkedQueue<ConnectTask>();
		this.handlerChain = new DefaultNioDispatcherEventLoopChain(this);
		this.packetDecoder = packetDecoder;
		this.packetEncoder = packetEncoder;
	}

	@Override
	public NioDispatcherEventLoopGroup getEventLoopGroup(){
		return (NioDispatcherEventLoopGroup)super.getEventLoopGroup();
	}

	@Override
	protected void doLoop() throws Exception{
		int count = this.selector.select(TIMEOUT);
		if(count > 0){
			this.processSelect();
		}
		this.processConnect();
		this.processRegist();
	}

	@Override
	protected void onStart() throws Exception{
		this.selector = Selector.open();
	}

	@Override
	protected void onClose() throws Exception{
		if(null != this.selector){
			this.selector.close();
		}
	}

	@Override
	public void postRegist(Connection connection){
		if(this.inEventLoop()){
			this.handlerChain.fireOnRegist(this,connection,this.selector);
		}else{
			this.registQueue.add(connection);
			this.selector.wakeup();
		}
	}
	
	@Override
	public void postConnect(Connection connection,Selector selector){
		if(this.inEventLoop()){
			this.handlerChain.fireOnConnect(this,connection,selector);
		}else{
			this.connectQueue.add(new ConnectTask(connection,selector));
		}
	}
	
	@Override
	public PacketEncoder getPacketEncoder(){
		return this.packetEncoder;
	}

	@Override
	public PacketDecoder getPacketDecoder(){
		return this.packetDecoder;
	}
	
	private class ConnectTask implements Runnable{
		private Connection connection;
		private Selector selector;
		private ConnectTask(Connection connection,Selector selector){
			this.connection = connection;
			this.selector = selector;
		}
		@Override
		public void run(){
			try{
				connection.postConnector(this.selector);
			}catch(IOException e){
				connection.close();
			}
		}
	}
	
	private void processConnect(){
		ConnectTask task = null;
		while((task = this.connectQueue.poll()) != null){
			task.run();
		}
	}
	
	private void processRegist(){
		Connection connection = null;
		while((connection = this.registQueue.poll()) != null){
			this.handlerChain.fireOnRegist(this,connection,this.selector);
		}
	}

	private void processSelect(){
		Iterator<SelectionKey> iter = this.selector.selectedKeys().iterator();
		while(iter.hasNext()){
			try{
				SelectionKey key = iter.next();
				Connection connection = (Connection)key.attachment();
				if(null == connection || (!connection.isOpen())){
					key.cancel();
					continue;
				}
				int readyOps = key.readyOps();
				if((readyOps & SelectionKey.OP_READ) != 0){
					this.processReaderEvent(connection);
				}else if((readyOps & SelectionKey.OP_WRITE) != 0){
					this.processWriterEvent(connection);
				}else if((readyOps & SelectionKey.OP_CONNECT) != 0){
					this.processConnecterEvent(connection,key.selector());
				}else{
					key.cancel();
				}
			}finally{
				iter.remove();
			}
		}
	}

	private void processReaderEvent(Connection connection){
		this.handlerChain.fireOnReader(this,connection);
	}

	private void processWriterEvent(Connection connection){
		this.handlerChain.fireOnReader(this,connection);
	}

	private void processConnecterEvent(Connection connection,Selector selector){
		this.handlerChain.fireOnConnect(this,connection,selector);
	}

	@Override
	public NioDispatcherEventLoopChain getHandlerChain(){
		return this.handlerChain;
	}

}