package com.jcute.network.reactor.connect;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.jcute.network.packet.Packet;
import com.jcute.network.reactor.dispacth.NioDispatcherEventLoop;

public interface Connection{
	
	public boolean isOpen();
	
	public boolean isServerSide();
	
	public boolean isClientSide();
	
	public SocketChannel getChannel();
	
	public NioDispatcherEventLoop getEventLoop();
	
	public void postRegist(Selector selector)throws IOException;
	
	public void postReader();
	
	public void postWriter();
	
	public void postConnector(Selector selector)throws IOException;
	
	public void close(Throwable cause);
	
	public void close();
	
	public void write(Packet packet);
	
	public void writeAndFlush(Packet packet);
	
	public void flush();
	
}