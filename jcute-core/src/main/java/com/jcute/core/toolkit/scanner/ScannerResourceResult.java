package com.jcute.core.toolkit.scanner;

import java.io.IOException;
import java.io.InputStream;

public interface ScannerResourceResult extends ScannerResult{
	
	public InputStream getTargetInputStream() throws IOException;
	
}