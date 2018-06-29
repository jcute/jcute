package com.jcute.core.config.support;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.util.StringUtils;

public abstract class AbstractConfigSource implements ConfigSource{
	
	protected String fileName;
	protected String fileExtension;
	
	public AbstractConfigSource(String fileName,String fileExtension){
		if(StringUtils.isEmpty(fileName)){
			throw new IllegalArgumentException("file name must not be null");
		}
		if(StringUtils.isEmpty(fileExtension)){
			throw new IllegalArgumentException("file extension must not be null");
		}
		this.fileName = fileName.toLowerCase();
		this.fileExtension = fileExtension.toLowerCase();
	}
	
	@Override
	public String getConfigFileName(){
		return this.fileName;
	}

	@Override
	public String getConfigFileExtension(){
		return this.fileExtension;
	}

	@Override
	public String getConfigValue(String configName){
		return this.getConfigValue(configName,null);
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
		if(obj instanceof AbstractConfigSource){
			return this.toString().equals(obj.toString());
		}
		return false;
	}
	
	@Override
	public String toString(){
		return String.format("[ConfigSource]%s.%s",this.getConfigFileName(),this.getConfigFileExtension());
	}
	
}