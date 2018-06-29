package com.jcute.core.config;

import java.io.InputStream;

public interface ConfigSourceConverter{
	
	public String getExtension();
	
	/**
	 * 
	 * @param inputStream
	 * @param fileName 不包含后缀,会自动截取后缀
	 * @return
	 */
	public ConfigSource convert(InputStream inputStream,String fileName)throws Exception;
	
}