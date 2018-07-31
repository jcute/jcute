package com.jcute.network.reactor.connect.support;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.network.packet.Packet;
import com.jcute.network.packet.PacketDecoder;
import com.jcute.network.packet.PacketEncoder;
import com.jcute.network.reactor.connect.ConnectionHandler;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;

public class DefaultConnection extends AbstractConnection{

	private static final Logger logger = LoggerFactory.getLogger(DefaultConnection.class);

	private boolean serverSide;
	private ByteBuffer buffer;

	private ConnectionHandler handler;

	private Queue<ByteBuffer> writeQueue = new ConcurrentLinkedQueue<ByteBuffer>();

	public DefaultConnection(boolean serverSide,SocketChannel channel,NioDispatcherEventLoop eventLoop,ConnectionHandler handler){
		super(channel,eventLoop);
		this.serverSide = serverSide;
		this.buffer = ByteBuffer.allocate(1024);
		this.handler = handler;
	}

	@Override
	public boolean isServerSide(){
		return this.serverSide == true;
	}

	@Override
	public boolean isClientSide(){
		return this.serverSide == false;
	}
	
	@Override
	public void postRegist(Selector selector) throws IOException{
		super.postRegist(selector);
		this.handler.onConnect(this);
	}
	
	@Override
	public void postReader(){
		try{
			int read = this.channel.read(this.buffer);
			if(read < 0){
				this.close();
				return;
			}
			if(read == 0){
				if(!this.isOpen()){
					this.close();
					return;
				}
			}
			this.postWriter();
		}catch(IOException e){
			this.close(e);
		}
	}

	@Override
	public void postWriter(){
		PacketDecoder packetDecoder = this.getEventLoop().getPacketDecoder();
		Packet packet = packetDecoder.decode(this.buffer);
		if(null != packet){
			this.handler.OnMessage(this,packet);
		}
		this.buffer.clear();
		this.key.interestOps(this.key.interestOps() & SelectionKey.OP_READ);
	}

	@Override
	public void write(Packet packet){
		if(null == packet){
			return;
		}
		PacketEncoder packetEncoder = this.getEventLoop().getPacketEncoder();
		ByteBuffer buffer = packetEncoder.encode(packet);
		if(null != buffer){
			this.writeQueue.add(buffer);
		}
	}

	@Override
	public void writeAndFlush(Packet packet){
		this.write(packet);
		this.flush();
		this.key.interestOps(this.key.interestOps() | SelectionKey.OP_READ);
	}

	@Override
	public void flush(){
		try{
			ByteBuffer byteBuffer = null;
			while((byteBuffer = this.writeQueue.poll()) != null){
				this.channel.write(byteBuffer);
				byteBuffer.clear();
			}
		}catch(IOException e){
			this.close(e);
		}
	}

	@Override
	public void close(Throwable cause){
		if(null != cause){
			this.handler.onException(this,cause);
		}
		if(null != this.buffer){
			this.buffer.clear();
		}
		this.handler.unConnect(this);
		if(null != this.key){
			this.key.cancel();
		}
		if(null != this.channel){
			try{
				this.channel.close();
				this.channel.socket().close();
			}catch(IOException e){
				logger.error(e.getMessage(),e);
			}
		}
	}

}