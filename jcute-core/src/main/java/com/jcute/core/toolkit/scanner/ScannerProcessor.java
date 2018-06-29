package com.jcute.core.toolkit.scanner;

public interface ScannerProcessor<T extends ScannerResult>{
	
	public boolean execute(T target);
	
}