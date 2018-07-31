package com.jcute.network.buffer.support;

import com.jcute.network.buffer.ByteProcessor;

public class ByteProcessorByNotIndexOf implements ByteProcessor{
	
	private final byte value;
	
	public ByteProcessorByNotIndexOf(byte value){
		this.value = value;
	}
	
	@Override
	public boolean process(byte value) throws Exception{
		return this.value == value;
	}
	
}