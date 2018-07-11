package com.jcute.core.net.toolkit;

public interface Releasable{
	
	public void release(long version);
	
	public long getReleaseVersion();
	
	public boolean isReleased();
	
}