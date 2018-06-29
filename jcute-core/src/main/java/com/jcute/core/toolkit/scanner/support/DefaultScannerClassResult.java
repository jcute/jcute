package com.jcute.core.toolkit.scanner.support;

import java.net.URL;

import com.jcute.core.toolkit.scanner.ScannerClassResult;
import com.jcute.core.toolkit.scanner.ScannerResultType;

public class DefaultScannerClassResult extends AbstractScannerResult implements ScannerClassResult{

	protected Class<?> targetClass;

	public DefaultScannerClassResult(URL url,Class<?> targetClass){
		super(url,targetClass.getName());
		this.targetClass = targetClass;
	}

	@Override
	public ScannerResultType getTargetType(){
		return ScannerResultType.Class;
	}

	@Override
	public Class<?> getTargetClass(){
		return this.targetClass;
	}

}