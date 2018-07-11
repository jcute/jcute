package com.jcute.core.net.buffer.support;

import java.nio.ByteBuffer;

import com.jcute.core.net.buffer.BufferAllocator;

public class OnPooledDirectBuffer extends AbstractDirectBuffer{

	public OnPooledDirectBuffer(BufferAllocator allocator,ByteBuffer memory){
		super(allocator,memory);
	}
	
}