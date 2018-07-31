package com.jcute.network.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public interface ByteBufferSetter{

	public abstract ByteBuffer setBoolean(int index,boolean value);

	public abstract ByteBuffer setByte(int index,int value);

	public abstract ByteBuffer setShort(int index,int value);

	public abstract ByteBuffer setShortLE(int index,int value);

	public abstract ByteBuffer setMedium(int index,int value);

	public abstract ByteBuffer setMediumLE(int index,int value);

	public abstract ByteBuffer setInt(int index,int value);

	public abstract ByteBuffer setIntLE(int index,int value);

	public abstract ByteBuffer setLong(int index,long value);

	public abstract ByteBuffer setLongLE(int index,long value);

	public abstract ByteBuffer setChar(int index,int value);

	public abstract ByteBuffer setFloat(int index,float value);

	@Deprecated
	public ByteBuffer setFloatLE(int index,float value);

	public abstract ByteBuffer setDouble(int index,double value);

	@Deprecated
	public ByteBuffer setDoubleLE(int index,double value);

	public abstract ByteBuffer setBytes(int index,ByteBuffer src);

	public abstract ByteBuffer setBytes(int index,ByteBuffer src,int length);

	public abstract ByteBuffer setBytes(int index,ByteBuffer src,int srcIndex,int length);

	public abstract ByteBuffer setBytes(int index,byte[] src);

	public abstract ByteBuffer setBytes(int index,byte[] src,int srcIndex,int length);

	public abstract ByteBuffer setBytes(int index,java.nio.ByteBuffer src);

	public abstract int setBytes(int index,InputStream in,int length) throws IOException;

	public abstract int setBytes(int index,ScatteringByteChannel in,int length) throws IOException;

	public abstract int setBytes(int index,FileChannel in,long position,int length) throws IOException;

	public abstract ByteBuffer setZero(int index,int length);

	public abstract int setCharSequence(int index,CharSequence sequence,Charset charset);

}