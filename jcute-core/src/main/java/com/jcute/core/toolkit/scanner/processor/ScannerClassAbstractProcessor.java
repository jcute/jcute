package com.jcute.core.toolkit.scanner.processor;

import java.lang.reflect.Modifier;

import com.jcute.core.toolkit.scanner.ScannerClassProcessor;
import com.jcute.core.toolkit.scanner.ScannerClassResult;

public class ScannerClassAbstractProcessor implements ScannerClassProcessor{
	
	private boolean ignoreInterface;
	
	public ScannerClassAbstractProcessor(){
		this.ignoreInterface = false;
	}
	
	public ScannerClassAbstractProcessor(boolean ignoreInterface){
		this.ignoreInterface = ignoreInterface;
	}
	
	@Override
	public boolean execute(ScannerClassResult target){
		Class<?> targetClass = target.getTargetClass();
		return ignoreInterface ? ((!targetClass.isInterface()) && Modifier.isAbstract(targetClass.getModifiers())) : Modifier.isAbstract(targetClass.getModifiers());
	}
	
}