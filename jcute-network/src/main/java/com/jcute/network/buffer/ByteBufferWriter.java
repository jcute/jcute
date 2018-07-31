package com.jcute.network.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public interface ByteBufferWriter{

	public abstract ByteBuffer writeBoolean(boolean value);

	public abstract ByteBuffer writeByte(int value);

	public abstract ByteBuffer writeShort(int value);

	public abstract ByteBuffer writeShortLE(int value);

	public abstract ByteBuffer writeMedium(int value);

	public abstract ByteBuffer writeMediumLE(int value);

	public abstract ByteBuffer writeInt(int value);

	public abstract ByteBuffer writeIntLE(int value);

	public abstract ByteBuffer writeLong(long value);

	public abstract ByteBuffer writeLongLE(long value);

	public abstract ByteBuffer writeChar(int value);

	public abstract ByteBuffer writeFloat(float value);

	@Deprecated
	public ByteBuffer writeFloatLE(float value);

	public abstract ByteBuffer writeDouble(double value);

	@Deprecated
	public ByteBuffer writeDoubleLE(double value);

	public abstract ByteBuffer writeBytes(ByteBuffer src);

	public abstract ByteBuffer writeBytes(ByteBuffer src,int length);

	public abstract ByteBuffer writeBytes(ByteBuffer src,int srcIndex,int length);

	public abstract ByteBuffer writeBytes(byte[] src);

	public abstract ByteBuffer writeBytes(byte[] src,int srcIndex,int length);

	public abstract ByteBuffer writeBytes(java.nio.ByteBuffer src);

	public abstract int writeBytes(InputStream in,int length) throws IOException;

	public abstract int writeBytes(ScatteringByteChannel in,int length) throws IOException;

	public abstract int writeBytes(FileChannel in,long position,int length) throws IOException;

	public abstract ByteBuffer writeZero(int length);

	public abstract int writeCharSequence(CharSequence sequence,Charset charset);

}