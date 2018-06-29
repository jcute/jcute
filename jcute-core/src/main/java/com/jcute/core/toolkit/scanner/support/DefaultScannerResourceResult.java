package com.jcute.core.toolkit.scanner.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.jcute.core.toolkit.scanner.ScannerResourceResult;
import com.jcute.core.toolkit.scanner.ScannerResultType;

public class DefaultScannerResourceResult extends AbstractScannerResult implements ScannerResourceResult{

	public DefaultScannerResourceResult(URL url,String name){
		super(url,name);
	}
	
	@Override
	public ScannerResultType getTargetType(){
		return ScannerResultType.Resource;
	}
	
	@Override
	public InputStream getTargetInputStream() throws IOException{
		return this.url.openConnection().getInputStream();
	}
	
}