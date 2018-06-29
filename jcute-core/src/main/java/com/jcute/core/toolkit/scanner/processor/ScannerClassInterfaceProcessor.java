package com.jcute.core.toolkit.scanner.processor;

import com.jcute.core.toolkit.scanner.ScannerClassProcessor;
import com.jcute.core.toolkit.scanner.ScannerClassResult;

public class ScannerClassInterfaceProcessor implements ScannerClassProcessor{

	@Override
	public boolean execute(ScannerClassResult target){
		Class<?> targetClass = target.getTargetClass();
		return targetClass.isInterface();
	}
	
}