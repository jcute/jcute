package com.jcute.core.net.toolkit.support;

import com.jcute.core.net.toolkit.ByteProcessor;

public class ByteProcessorForUnIndexOf implements ByteProcessor{

	private final byte data;

	public ByteProcessorForUnIndexOf(byte data){
		this.data = data;
	}

	@Override
	public boolean process(byte value) throws Exception{
		return value == this.data;
	}
	
}