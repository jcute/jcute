package com.jcute.core.toolkit.scanner;

import java.util.Set;

public interface ScannerFilter{
	
	public boolean execute(String path,ScannerResultType type,Set<String> scannerPatterns);
	
}