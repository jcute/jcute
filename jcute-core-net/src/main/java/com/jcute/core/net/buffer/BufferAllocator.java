package com.jcute.core.net.buffer;

public interface BufferAllocator{
	
	public Buffer allocate(int limit);
	
	public Buffer allocate(int limit,int capacity);
	
	public Buffer reallocate(Buffer buffer,int limit);
	
	public Buffer reallocate(Buffer buffer,int limit,boolean copy);
	
	public Buffer reallocate(Buffer buffer,int limit,int capacity);
	
	public Buffer reallocate(Buffer buffer,int limit,int capacity,boolean copy);
	
	public int getUnitMemorySize();
	
	public int getCapacity();
	
	public void release(Buffer buffer);
	
	public void freeMemory();
	
	public boolean isDirect();
	
}