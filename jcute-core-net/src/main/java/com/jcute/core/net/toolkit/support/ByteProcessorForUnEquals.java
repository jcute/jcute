package com.jcute.core.net.toolkit.support;

import com.jcute.core.net.toolkit.ByteProcessor;

public class ByteProcessorForUnEquals implements ByteProcessor{

	private final byte[] data;

	public ByteProcessorForUnEquals(byte...data){
		this.data = data;
	}

	@Override
	public boolean process(byte value) throws Exception{
		for(byte b : this.data){
			if(b == value){
				return true;
			}
		}
		return false;
	}
	
}