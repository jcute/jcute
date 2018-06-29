package com.jcute.core.config.converter;

import java.io.InputStream;
import java.util.Properties;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.source.DefaultConfigSourceForPropertiesFile;
import com.jcute.core.config.support.AbstractConfigSourceConverter;

public class ConfigSourceConverterForProperties extends AbstractConfigSourceConverter{
	
	@Override
	public String getExtension(){
		return ConfigSource.EXTENSION_PROPERTIES;
	}
	
	@Override
	protected ConfigSource doConvert(InputStream inputStream,String fileName)throws Exception{
		try{
			Properties properties = new Properties();
			properties.load(inputStream);
			return new DefaultConfigSourceForPropertiesFile(fileName,properties);
		}finally{
			inputStream.close();
		}
	}
	
}