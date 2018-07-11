package com.jcute.core.net.toolkit.support;

import com.jcute.core.net.toolkit.ByteProcessor;

public class ByteProcessorForOnIndexOf implements ByteProcessor{

	private final byte data;

	public ByteProcessorForOnIndexOf(byte data){
		this.data = data;
	}

	@Override
	public boolean process(byte value) throws Exception{
		return value != this.data;
	}
	
}