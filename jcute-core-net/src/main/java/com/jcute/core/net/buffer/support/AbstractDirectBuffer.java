package com.jcute.core.net.buffer.support;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.jcute.core.net.buffer.Buffer;
import com.jcute.core.net.buffer.BufferAllocator;
import com.jcute.core.net.toolkit.ByteProcessor;

public abstract class AbstractDirectBuffer extends AbstractBuffer{

	protected ByteBuffer memory;

	public AbstractDirectBuffer(BufferAllocator allocator,ByteBuffer memory){
		super(allocator);
		this.memory = memory;
	}

	@Override
	public byte[] array(){
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(){
		return this.memory.get();
	}

	@Override
	public byte getByte(int index){
		return this.memory.get(this.index(index));
	}

	@Override
	public void get(byte[] dst,int offset,int length){
		this.memory.get(dst,offset,length);
	}

	@Override
	public int getInt(){
		return this.memory.getInt();
	}

	@Override
	public int getInt(int index){
		return this.memory.getInt(this.index(index));
	}

	@Override
	public long getLong(){
		return this.memory.getLong();
	}

	@Override
	public long getLong(int index){
		return this.memory.getLong(this.index(index));
	}

	@Override
	public int getIntLE(){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		int v = this.memory.getInt();
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public int getIntLE(int index){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		int v = this.memory.getInt(this.index(index));
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public long getLongLE(){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		long v = this.memory.getLong();
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public long getLongLE(int index){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		long v = this.memory.getLong(this.index(index));
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public short getShort(){
		return this.memory.getShort();
	}

	@Override
	public short getShort(int index){
		return this.memory.getShort(this.index(index));
	}

	@Override
	public short getShortLE(){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		short v = this.memory.getShort();
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public short getShortLE(int index){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		short v = this.memory.getShort(this.index(index));
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public short getUnsignedByte(){
		return (short)(getByte() & 0xff);
	}

	@Override
	public short getUnsignedByte(int index){
		return (short)(getByte(index) & 0xff);
	}

	@Override
	public long getUnsignedInt(){
		long v = this.toUnsignedInt(this.memory.getInt());
		return v;
	}

	@Override
	public long getUnsignedInt(int index){
		return this.toUnsignedInt(this.memory.getInt(this.index(index)));
	}

	@Override
	public long getUnsignedIntLE(){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		long v = toUnsignedInt(this.memory.getInt());
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public long getUnsignedIntLE(int index){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		long v = toUnsignedInt(this.memory.getInt(this.index(index)));
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public int getUnsignedShort(){
		return this.memory.getShort() & 0xffff;
	}

	@Override
	public int getUnsignedShort(int index){
		return this.memory.getShort(this.index(index)) & 0xffff;
	}

	@Override
	public int getUnsignedShortLE(){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		int v = this.memory.getShort() & 0xffff;
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public int getUnsignedShortLE(int index){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		int v = this.memory.getShort(this.index(index)) & 0xff;
		this.memory.order(ByteOrder.BIG_ENDIAN);
		return v;
	}

	@Override
	public boolean hasArray(){
		return false;
	}

	@Override
	public void put(byte[] src,int offset,int length){
		this.memory.put(src,offset,length);
	}

	@Override
	public void putByte(byte src){
		this.memory.put(src);
	}

	@Override
	public void putShort(short value){
		this.memory.putShort(value);
	}

	@Override
	public void putShortLE(short value){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		this.memory.putShort(value);
		this.memory.order(ByteOrder.BIG_ENDIAN);
	}

	@Override
	public void putUnsignedShort(int value){
		byte b1 = (byte)(value & 0xff);
		byte b0 = (byte)(value >> 8 * 1);
		memory.put(b0);
		memory.put(b1);
	}

	@Override
	public void putUnsignedShortLE(int value){
		byte b0 = (byte)(value & 0xff);
		byte b1 = (byte)(value >> 8 * 1);
		memory.put(b0);
		memory.put(b1);
	}

	@Override
	public void putInt(int value){
		memory.putInt(value);
	}

	@Override
	public void putIntLE(int value){
		memory.order(ByteOrder.LITTLE_ENDIAN);
		memory.putInt(value);
		memory.order(ByteOrder.BIG_ENDIAN);
	}

	@Override
	public void putUnsignedInt(long value){
		byte b3 = (byte)((value & 0xff));
		byte b2 = (byte)((value >> 8 * 1) & 0xff);
		byte b1 = (byte)((value >> 8 * 2) & 0xff);
		byte b0 = (byte)((value >> 8 * 3));
		this.memory.put(b0);
		this.memory.put(b1);
		this.memory.put(b2);
		this.memory.put(b3);
	}

	@Override
	public void putUnsignedIntLE(long value){
		byte b0 = (byte)((value & 0xff));
		byte b1 = (byte)((value >> 8 * 1) & 0xff);
		byte b2 = (byte)((value >> 8 * 2) & 0xff);
		byte b3 = (byte)((value >> 8 * 3));
		this.memory.put(b0);
		this.memory.put(b1);
		this.memory.put(b2);
		this.memory.put(b3);
	}

	@Override
	public void putLong(long value){
		this.memory.putLong(value);
	}

	@Override
	public void putLongLE(long value){
		this.memory.order(ByteOrder.LITTLE_ENDIAN);
		this.memory.putLong(value);
		this.memory.order(ByteOrder.BIG_ENDIAN);
	}

	@Override
	public Buffer clear(){
		this.memory.position(this.offset).limit(this.index(this.capacity));
		return this;
	}

	@Override
	public Buffer flip(){
		this.memory.limit(this.memory.position());
		this.memory.position(this.offset);
		return this;
	}

	@Override
	public boolean hasRemaining(){
		return this.memory.hasRemaining();
	}

	@Override
	public int limit(){
		return this.memory.limit() - offset;
	}

	@Override
	public Buffer limit(int limit){
		this.memory.limit(this.index(limit));
		return this;
	}

	@Override
	public ByteBuffer nioBuffer(){
		return this.memory;
	}

	@Override
	public int position(){
		return this.memory.position() - offset;
	}

	@Override
	public Buffer position(int position){
		this.memory.position(this.index(position));
		return this;
	}

	@Override
	public int remaining(){
		return memory.remaining();
	}

	@Override
	public Buffer reverse(){
		return this;
	}

	@Override
	public Buffer markPosition(){
		this.memory.mark();
		return this;
	}

	@Override
	public Buffer resetPosition(){
		this.memory.reset();
		return this;
	}

	@Override
	public ByteBuffer getNioBuffer(){
		return this.memory;
	}

	@Override
	public int eachByte(ByteProcessor processor,int index,int length){
		int start = this.index(index);
		int end = start + length;
		try{
			for(int i = start;i < end;i++){
				if(!processor.process(this.getByte(i))){
					return i - start;
				}
			}
		}catch(Exception e){}
		return -1;
	}

	@Override
	public int eachByteDesc(ByteProcessor processor,int index,int length){
		int start = this.index(index);
		int end = start + length;
		try{
			for(int i = end;i >= start;i--){
				if(!processor.process(getByte(i))){
					return i - start;
				}
			}
		}catch(Exception e){}
		return -1;
	}

	@Override
	protected int doRead(ByteBuffer buffer,int srcRemaining,int rawRemaining){
		if(srcRemaining > rawRemaining){
			if(buffer.hasArray()){
				this.put(buffer.array(),buffer.position(),rawRemaining);
				buffer.position(buffer.position() + rawRemaining);
				return rawRemaining;
			}else{
				int oldLimit = buffer.limit();
				int oldPosition = buffer.position();
				buffer.limit(oldPosition + rawRemaining);
				this.memory.put(buffer);
				buffer.limit(oldLimit);
				return rawRemaining;
			}
		}else{
			if(buffer.hasArray()){
				this.put(buffer.array(),buffer.position(),srcRemaining);
				buffer.position(buffer.limit());
				return srcRemaining;
			}else{
				this.memory.put(buffer);
				return srcRemaining;
			}
		}
	}

	@Override
	protected int doRead(Buffer buffer,int srcRemaining,int rawRemaining){
		if(srcRemaining > rawRemaining){
			if(buffer.hasArray()){
				this.put(buffer.array(),buffer.position(),rawRemaining);
				buffer.position(buffer.position() + rawRemaining);
				return rawRemaining;
			}else{
				ByteBuffer nioBuffer = buffer.nioBuffer();
				int oldLimit = nioBuffer.limit();
				int oldPosition = nioBuffer.position();
				nioBuffer.limit(oldPosition + rawRemaining);
				this.memory.put(nioBuffer);
				nioBuffer.limit(oldLimit);
				return rawRemaining;
			}
		}else{
			if(buffer.hasArray()){
				this.put(buffer.array(),buffer.position(),srcRemaining);
				buffer.position(buffer.limit());
				return srcRemaining;
			}else{
				this.memory.put(buffer.nioBuffer());
				return srcRemaining;
			}
		}
	}

	protected long toUnsignedInt(int value){
		if(value < 0){
			return value & 0xffffffffffffffffL;
		}
		return value;
	}

}