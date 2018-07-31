package com.jcute.network.buffer;

import java.nio.charset.Charset;

import com.jcute.network.toolkit.ReferenceCounter;

public interface ByteBuffer extends ByteBufferGetter, ByteBufferSetter, ByteBufferReader, ByteBufferWriter, ReferenceCounter, Comparable<ByteBuffer>{

	public int capacity();

	public ByteBuffer capacity(int newCapacity);

	public int maxCapacity();

	public ByteBufferAllocator alloc();

	public ByteBuffer unwrap();

	public boolean isDirect();

	public boolean isReadOnly();

	public ByteBuffer asReadOnly();

	public int readerIndex();

	public ByteBuffer readerIndex(int readerIndex);

	public int writerIndex();

	public ByteBuffer writerIndex(int writerIndex);

	public ByteBuffer setIndex(int readerIndex,int writerIndex);

	public int readableBytes();

	public int writableBytes();

	public int maxWritableBytes();

	public boolean isReadable();

	public boolean isReadable(int size);

	public boolean isWritable();

	public boolean isWritable(int size);

	public ByteBuffer clear();

	public ByteBuffer markReaderIndex();

	public ByteBuffer markWriterIndex();

	public ByteBuffer resetReaderIndex();

	public ByteBuffer resetWriterIndex();

	public ByteBuffer discardReadBytes();

	public ByteBuffer discardSomeReadBytes();

	public ByteBuffer ensureWritable(int minWritableBytes);

	public int ensureWritable(int minWritableBytes,boolean force);

	public ByteBuffer skipBytes(int length);

	public abstract int indexOf(int fromIndex,int toIndex,byte value);

	public abstract int bytesBefore(byte value);

	public abstract int bytesBefore(int length,byte value);

	public abstract int bytesBefore(int index,int length,byte value);

	public abstract int forEachByte(ByteProcessor processor);

	public abstract int forEachByte(int index,int length,ByteProcessor processor);

	public abstract int forEachByteDesc(ByteProcessor processor);

	public abstract int forEachByteDesc(int index,int length,ByteProcessor processor);

	public ByteBuffer copy();

	public ByteBuffer copy(int index,int length);

	public ByteBuffer slice();

	public ByteBuffer retainedSlice();

	public ByteBuffer slice(int index,int length);

	public ByteBuffer retainedSlice(int index,int length);

	public ByteBuffer duplicate();

	public ByteBuffer retainedDuplicate();

	public abstract int nioBufferCount();

	public java.nio.ByteBuffer nioBuffer();

	public java.nio.ByteBuffer nioBuffer(int index,int length);

	public java.nio.ByteBuffer internalNioBuffer(int index,int length);

	public java.nio.ByteBuffer[] nioBuffers();

	public java.nio.ByteBuffer[] nioBuffers(int index,int length);

	public abstract boolean hasArray();

	public abstract byte[] array();

	public abstract int arrayOffset();

	public abstract boolean hasMemoryAddress();

	public abstract long memoryAddress();

	public abstract String toString(Charset charset);

	public abstract String toString(int index,int length,Charset charset);

	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int compareTo(ByteBuffer buffer);

	@Override
	public abstract String toString();

	@Override
	public ByteBuffer retain(int increment);

	@Override
	public ByteBuffer retain();

	@Override
	public ByteBuffer touch();

	@Override
	public ByteBuffer touch(Object hint);

}