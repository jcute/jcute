package com.jcute.core.network;

public interface NetWorkHandlerFactory<H extends NetWorkHandler,E extends NetWorkHandlerEncoder,D extends NetWorkHandlerDecoder>{

	public H createHandler();

	public E createEncoder();

	public D createDecoder();

}