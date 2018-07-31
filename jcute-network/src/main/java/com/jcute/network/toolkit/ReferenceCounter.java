package com.jcute.network.toolkit;

public interface ReferenceCounter{

	public int getReferenceCount();

	public ReferenceCounter retain();

	public ReferenceCounter retain(int increment);

	public ReferenceCounter touch();

	public ReferenceCounter touch(Object hint);

	public boolean release();

	public boolean release(int decrement);

}