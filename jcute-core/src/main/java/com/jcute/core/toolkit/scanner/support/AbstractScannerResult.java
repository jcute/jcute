package com.jcute.core.toolkit.scanner.support;

import java.net.URL;

import com.jcute.core.toolkit.scanner.ScannerResult;
import com.jcute.core.toolkit.scanner.ScannerResultType;
import com.jcute.core.util.StringUtils;

public abstract class AbstractScannerResult implements ScannerResult{
	
	protected URL url;
	protected String name;
	
	protected AbstractScannerResult(URL url,String name){
		if(null == url){
			throw new IllegalArgumentException("url must not be null");
		}
		if(StringUtils.isEmpty(name)){
			throw new IllegalArgumentException("name mustnot be empty");
		}
		this.url = url;
		this.name = name;
	}
	
	@Override
	public URL getTargetURL(){
		return this.url;
	}

	@Override
	public String getTargetName(){
		return this.name;
	}
	
	@Override
	public boolean isTargetClass(){
		return this.getTargetType() == ScannerResultType.Class;
	}

	@Override
	public boolean isTargetResource(){
		return this.getTargetType() == ScannerResultType.Resource;
	}

	@Override
	public int hashCode(){
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(null == obj){
			return false;
		}
		if(obj instanceof AbstractScanner){
			return obj.toString().equals(this.toString());
		}
		return false;
	}

	@Override
	public String toString(){
		return String.format("[%s]%s#%s",this.getTargetType(),this.getTargetURL(),this.getTargetName());
	}
	
}