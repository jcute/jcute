package com.jcute.core.net.buffer.support;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import com.jcute.core.net.buffer.Buffer;
import com.jcute.core.net.buffer.BufferAllocator;
import com.jcute.core.net.buffer.exception.BufferException;
import com.jcute.core.net.toolkit.ByteProcessor;

public abstract class AbstractBuffer implements Buffer{

	private static final AtomicIntegerFieldUpdater<AbstractBuffer> updater;

	static{
		updater = AtomicIntegerFieldUpdater.newUpdater(AbstractBuffer.class,"counter");
	}

	protected volatile int counter = 0;

	protected BufferAllocator allocator;
	protected int offset;
	protected int capacity;
	protected int markLimit;
	protected long releaseVersion;

	public AbstractBuffer(BufferAllocator allocator){
		this.allocator = allocator;
	}

	@Override
	public int eachByte(ByteProcessor processor){
		return this.eachByte(processor,this.position(),this.limit());
	}

	@Override
	public int eachByteDesc(ByteProcessor processor){
		return this.eachByteDesc(processor,this.position(),this.limit());
	}

	@Override
	public void get(byte[] dst){
		this.get(dst,0,dst.length);
	}

	@Override
	public byte[] getBytes(){
		byte[] bytes = new byte[this.remaining()];
		this.get(bytes);
		return bytes;
	}

	@Override
	public int capacity(){
		return this.capacity;
	}

	@Override
	public int offset(){
		return this.offset;
	}

	@Override
	public void put(byte[] src){
		this.put(src,0,src.length);
	}

	@Override
	public int read(Buffer src){
		int srcRemaining = src.remaining();
		if(srcRemaining == 0){
			return 0;
		}
		int rawRemaining = this.remaining();
		if(rawRemaining == 0){
			return 0;
		}
		return this.doRead(src,srcRemaining,rawRemaining);
	}

	@Override
	public int read(Buffer src,int length){
		int srcRemaining = src.remaining();
		if(srcRemaining == 0){
			return 0;
		}
		int rawRemaining = this.remaining();
		if(rawRemaining == 0){
			return 0;
		}
		if(srcRemaining > length){
			int limit = src.limit();
			src.limit(src.position() + length);
			int len = this.doRead(src,length,rawRemaining);
			src.limit(limit);
			return len;
		}else{
			return this.doRead(src,srcRemaining,rawRemaining);
		}
	}

	@Override
	public int read(ByteBuffer src){
		int srcRemaining = src.remaining();
		if(srcRemaining == 0){
			return 0;
		}
		int rawRemaining = this.remaining();
		if(rawRemaining == 0){
			return 0;
		}
		return this.doRead(src,srcRemaining,rawRemaining);
	}

	@Override
	public int read(ByteBuffer src,int length){
		int srcRemaining = src.remaining();
		if(srcRemaining == 0){
			return 0;
		}
		int rawRemaining = this.remaining();
		if(rawRemaining == 0){
			return 0;
		}
		if(srcRemaining > length){
			int limit = src.limit();
			src.limit(src.position() + length);
			int len = this.doRead(src,length,rawRemaining);
			src.limit(limit);
			return len;
		}else{
			return this.doRead(src,srcRemaining,rawRemaining);
		}
	}

	@Override
	public Buffer skipBytes(int length){
		return this.position(this.position() + length);
	}

	@Override
	public Buffer reallocate(int limit){
		return this.reallocate(limit,false);
	}

	@Override
	public Buffer reallocate(int limit,boolean copy){
		return this.allocator.reallocate(this,limit,copy);
	}

	@Override
	public Buffer reallocate(int limit,int capacity,boolean copy){
		if(limit < 1){
			throw new BufferException(String.format("illegal limit: %d",limit));
		}
		if(limit > capacity){
			throw new BufferException(String.format("limit: %d capacity: %d",limit,capacity));
		}
		return this.reallocate(limit,copy);
	}

	@Override
	public Buffer reallocate(int limit,int capacity){
		return this.reallocate(limit,capacity,false);
	}

	@Override
	public void release(long version){
		if(this.releaseVersion != version){
			return;
		}
		int count = this.counter;
		if(count < 1){
			return;
		}
		if(updater.compareAndSet(this,count,count - 1)){
			if(count == 1){
				this.allocator.release(this);
				return;
			}
		}
		for(;;){
			count = this.counter;
			if(count < 1){
				return;
			}
			if(updater.compareAndSet(this,count,count - 1)){
				if(count == 1){
					this.allocator.release(this);
					return;
				}
			}
		}
	}

	@Override
	public long getReleaseVersion(){
		return this.releaseVersion;
	}

	@Override
	public boolean isReleased(){
		return this.releaseVersion < 1;
	}

	@Override
	public Buffer markLimit(){
		this.markLimit = this.limit();
		return this;
	}

	@Override
	public Buffer resetLimit(){
		return this.limit(this.markLimit);
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName());
		sb.append("[");
		sb.append("position=").append(this.position()).append(",");
		sb.append("limit=").append(this.limit()).append(",");
		sb.append("capacity=").append(this.capacity).append(",");
		sb.append("remaining=").append(this.remaining()).append(",");
		sb.append("offset=").append(this.offset());
		sb.append("]");
		return sb.toString();
	}

	protected void attachReferenceCount(){
		int count = this.counter;
		if(updater.compareAndSet(this,count,count + 1)){
			return;
		}
		for(;;){
			count = this.counter;
			if(updater.compareAndSet(this,count,count + 1)){
				break;
			}
		}
	}

	protected void offset(int offset){
		this.offset = offset;
	}

	protected int index(int index){
		return this.offset + index;
	}

	protected abstract int doRead(Buffer buffer,int srcRemaining,int rawRemaining);

	protected abstract int doRead(ByteBuffer buffer,int srcRemaining,int rawRemaining);

}