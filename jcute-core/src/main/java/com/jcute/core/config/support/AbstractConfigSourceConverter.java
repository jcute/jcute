package com.jcute.core.config.support;

import java.io.InputStream;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.ConfigSourceConverter;

public abstract class AbstractConfigSourceConverter implements ConfigSourceConverter{

	@Override
	public ConfigSource convert(InputStream inputStream,String fileName) throws Exception{
		if(null == inputStream){
			throw new IllegalArgumentException("input stream must not be null");
		}
		if(null == fileName){
			throw new IllegalArgumentException("file name must not be null");
		}
		if(fileName.indexOf(".") != -1){
			fileName = fileName.substring(0,fileName.lastIndexOf("."));
		}
		return this.doConvert(inputStream,fileName);
	}
	
	protected abstract ConfigSource doConvert(InputStream inputStream,String fileName)throws Exception;
	
}