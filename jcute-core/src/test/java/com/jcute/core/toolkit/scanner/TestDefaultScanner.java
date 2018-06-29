package com.jcute.core.toolkit.scanner;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.jcute.core.toolkit.scanner.processor.ScannerClassAbstractProcessor;
import com.jcute.core.toolkit.scanner.processor.ScannerClassInterfaceProcessor;
import com.jcute.core.toolkit.scanner.support.DefaultScanner;

public class TestDefaultScanner{

	@Test
	@Ignore
	public void testScan() throws IOException{

		Scanner scanner = new DefaultScanner();
		scanner.setClassLoader(this.getClass().getClassLoader());
//		scanner.attachClassPattern("com.jcute.core.toolkit.logging");
//		scanner.attachClassPattern("org.apache.log4j.?DC");
		scanner.attachResourcePattern("application.xml");
		scanner.attachResourcePattern("META-INF/MANIFEST.MF");
//		scanner.attachResourcePattern("META-INF/application.xml");
//		scanner.attachResourcePattern("META-INF/spring/spring-*.xml");
		
		for(ScannerResult scannerResult : scanner.scan()){
			if(scannerResult.isTargetResource()){
				ScannerResourceResult scannerResourceResult = (ScannerResourceResult)scannerResult;
				InputStream inputStream = scannerResourceResult.getTargetInputStream();
				String resource = new java.util.Scanner(inputStream).useDelimiter("\\Z").next();
				System.out.println(resource);
				inputStream.close();
			}
			System.out.println(scannerResult.getTargetName());
		}
		
	}
	
	@Test
	@Ignore
	public void testInterfaceProcessor(){
		Scanner scanner = new DefaultScanner();
		scanner.setClassLoader(this.getClass().getClassLoader());
		scanner.attachClassPattern("com.jcute.core.toolkit.scanner");
		scanner.attachScannerClassProcessor(new ScannerClassInterfaceProcessor());
		for(ScannerResult scannerResult : scanner.scan()){
			System.out.println(scannerResult.getTargetName());
		}
	}
	
	@Test
	@Ignore
	public void testAbstractProcessor(){
		Scanner scanner = new DefaultScanner();
		scanner.setClassLoader(this.getClass().getClassLoader());
		scanner.attachClassPattern("com.jcute.core.toolkit.scanner");
		scanner.attachScannerClassProcessor(new ScannerClassAbstractProcessor(true));//true 代表忽略interface , false不忽略,默认为false
		for(ScannerResult scannerResult : scanner.scan()){
			System.out.println(scannerResult.getTargetName());
		}
	}
	
	@Test
	@Ignore
	public void testScanResource() throws IOException{
		Scanner scanner = new DefaultScanner();
		scanner.setClassLoader(this.getClass().getClassLoader());
		scanner.attachResourcePattern("application.xml");
		for(ScannerResult scannerResult : scanner.scan()){
			if(scannerResult.isTargetResource()){
				ScannerResourceResult scannerResourceResult = (ScannerResourceResult)scannerResult;
				InputStream inputStream = scannerResourceResult.getTargetInputStream();
				String resource = new java.util.Scanner(inputStream).useDelimiter("\\Z").next();
				System.out.println(resource);
				inputStream.close();
			}
		}
	}
	
	@Test
	@Ignore
	public void testScanPattern() throws IOException{
		
		System.in.read();
		
		Scanner scanner = new DefaultScanner();
		scanner.setClassLoader(this.getClass().getClassLoader());
		scanner.attachClassPattern("com.jcute.core.toolkit.scanner");
		scanner.attachClassPattern("org.apache.log4j");
		scanner.attachResourcePattern("application.xml");
		scanner.attachResourcePattern("META-INF/MANIFEST.MF");
		for(ScannerResult scannerResult : scanner.scan()){
			System.out.println(scannerResult);
		}
	}
	
}