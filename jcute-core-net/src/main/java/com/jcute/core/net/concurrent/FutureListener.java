package com.jcute.core.net.concurrent;

public interface FutureListener{

	public void complete(Future future) throws Exception;

}