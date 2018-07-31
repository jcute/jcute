package com.jcute.network.buffer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.charset.Charset;

public interface ByteBufferReader{

	public abstract boolean readBoolean();

	public abstract byte readByte();

	public abstract short readUnsignedByte();

	public abstract short readShort();

	public abstract short readShortLE();

	public abstract int readUnsignedShort();

	public abstract int readUnsignedShortLE();

	public abstract int readMedium();

	public abstract int readMediumLE();

	public abstract int readUnsignedMedium();

	public abstract int readUnsignedMediumLE();

	public abstract int readInt();

	public abstract int readIntLE();

	public abstract long readUnsignedInt();

	public abstract long readUnsignedIntLE();

	public abstract long readLong();

	public abstract long readLongLE();

	public abstract char readChar();

	public abstract float readFloat();

	@Deprecated
	public float readFloatLE();

	public abstract double readDouble();

	@Deprecated
	public double readDoubleLE();

	public abstract ByteBuffer readBytes(int length);

	public abstract ByteBuffer readSlice(int length);

	public abstract ByteBuffer readRetainedSlice(int length);

	public abstract ByteBuffer readBytes(ByteBuffer dst);

	public abstract ByteBuffer readBytes(ByteBuffer dst,int length);

	public abstract ByteBuffer readBytes(ByteBuffer dst,int dstIndex,int length);

	public abstract ByteBuffer readBytes(byte[] dst);

	public abstract ByteBuffer readBytes(byte[] dst,int dstIndex,int length);

	public abstract ByteBuffer readBytes(java.nio.ByteBuffer dst);

	public abstract ByteBuffer readBytes(OutputStream out,int length) throws IOException;

	public abstract int readBytes(GatheringByteChannel out,int length) throws IOException;

	public abstract CharSequence readCharSequence(int length,Charset charset);

	public abstract int readBytes(FileChannel out,long position,int length) throws IOException;

}