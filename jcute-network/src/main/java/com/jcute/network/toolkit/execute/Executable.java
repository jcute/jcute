package com.jcute.network.toolkit.execute;

public interface Executable{

	public void start() throws Exception;

	public void close() throws Exception;
	
	public boolean isRunning();
	
}