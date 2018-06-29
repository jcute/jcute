package com.jcute.core.toolkit.scanner;

import java.net.URL;

public interface ScannerResult{
	
	public URL getTargetURL();
	
	public String getTargetName();
	
	public ScannerResultType getTargetType();
	
	public boolean isTargetClass();
	
	public boolean isTargetResource();
	
}