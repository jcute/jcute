package com.jcute.network.buffer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.charset.Charset;

public interface ByteBufferGetter{

	public boolean getBoolean(int index);

	public byte getByte(int index);

	public short getUnsignedByte(int index);

	public short getShort(int index);

	public short getShortLE(int index);

	public int getUnsignedShort(int index);

	public int getUnsignedShortLE(int index);

	public int getMedium(int index);

	public int getMediumLE(int index);

	public int getUnsignedMedium(int index);

	public int getUnsignedMediumLE(int index);

	public int getInt(int index);

	public int getIntLE(int index);

	public long getUnsignedInt(int index);

	public long getUnsignedIntLE(int index);

	public long getLong(int index);

	public long getLongLE(int index);

	public char getChar(int index);

	public float getFloat(int index);

	@Deprecated
	public float getFloatLE(int index);

	public double getDouble(int index);

	@Deprecated
	public double getDoubleLE(int index);

	public ByteBuffer getBytes(int index,ByteBuffer dst);

	public ByteBuffer getBytes(int index,ByteBuffer dst,int length);

	public ByteBuffer getBytes(int index,ByteBuffer dst,int dstIndex,int length);

	public ByteBuffer getBytes(int index,byte[] dst);

	public ByteBuffer getBytes(int index,byte[] dst,int dstIndex,int length);

	public ByteBuffer getBytes(int index,java.nio.ByteBuffer dst);

	public ByteBuffer getBytes(int index,OutputStream out,int length) throws IOException;

	public int getBytes(int index,GatheringByteChannel out,int length) throws IOException;

	public int getBytes(int index,FileChannel out,long position,int length) throws IOException;

	public abstract CharSequence getCharSequence(int index,int length,Charset charset);

}