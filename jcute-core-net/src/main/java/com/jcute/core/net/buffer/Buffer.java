package com.jcute.core.net.buffer;

import java.nio.ByteBuffer;

import com.jcute.core.net.toolkit.ByteProcessor;
import com.jcute.core.net.toolkit.Releasable;

public interface Buffer extends Releasable{
	
	public byte[] array();
	
	public int capacity();
	
	public Buffer clear();
	
	public Buffer flip();
	
	public int eachByte(ByteProcessor processor);
	public int eachByte(ByteProcessor processor,int index,int length);
	public int eachByteDesc(ByteProcessor processor);
	public int eachByteDesc(ByteProcessor processor,int index,int length);
	
	public void get(byte[] dst);
	public void get(byte[] dst,int offset,int length);
	
	public byte getByte();
	public byte getByte(int index);
	
	public byte[] getBytes();
	
	public int getInt();
	public int getInt(int index);
	
	public int getIntLE();
	public int getIntLE(int index);
	
	public long getLong();
	public long getLong(int index);
	
	public long getLongLE();
	public long getLongLE(int index);
	
	public short getShort();
	public short getShort(int index);
	
	public short getShortLE();
	public short getShortLE(int index);
	
	public short getUnsignedByte();
	public short getUnsignedByte(int index);
	
	public long getUnsignedInt();
	public long getUnsignedInt(int index);
	
	public long getUnsignedIntLE();
	public long getUnsignedIntLE(int index);
	
	public int getUnsignedShort();
	public int getUnsignedShort(int index);
	
	public int getUnsignedShortLE();
	public int getUnsignedShortLE(int index);
	
	public boolean hasArray();
	public boolean hasRemaining();
	
	public int limit();
	public Buffer limit(int limit);
	
	public Buffer markPosition();
	public Buffer markLimit();
	
	public int offset();
	
	public int position();
	public Buffer position(int position);
	
	public void put(byte[] src);
	public void put(byte[] src,int offset,int length);
	
	public void putByte(byte src);
	public void putInt(int src);
	public void putIntLE(int src);
	public void putLong(long src);
	public void putLongLE(long src);
	public void putShort(short src);
	public void putShortLE(short src);
	public void putUnsignedInt(long src);
	public void putUnsignedIntLE(long src);
	public void putUnsignedShort(int src);
	public void putUnsignedShortLE(int src);
	
	public int read(Buffer src);
	public int read(Buffer src,int length);
	public int read(ByteBuffer src);
	public int read(ByteBuffer src,int length);
	
	public Buffer reallocate(int limit);
	public Buffer reallocate(int limit,boolean copy);
	public Buffer reallocate(int limit,int capacity);
	public Buffer reallocate(int limit,int capacity,boolean copy);
	
	public int remaining();
	
	public Buffer resetPosition();
	public Buffer resetLimit();
	public Buffer reverse();
	public Buffer skipBytes(int length);
	
	public ByteBuffer nioBuffer();
	
	public ByteBuffer getNioBuffer();
	
}