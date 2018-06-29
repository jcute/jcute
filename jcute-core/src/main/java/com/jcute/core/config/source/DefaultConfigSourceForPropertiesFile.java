package com.jcute.core.config.source;

import java.util.Properties;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.support.AbstractConfigSource;

public class DefaultConfigSourceForPropertiesFile extends AbstractConfigSource{
	
	private Properties properties;

	public DefaultConfigSourceForPropertiesFile(String fileName,Properties properties){
		super(fileName,ConfigSource.EXTENSION_PROPERTIES);
		if(null == properties){
			throw new IllegalArgumentException("properties file must not be null");
		}
		this.properties = properties;
	}

	@Override
	public boolean hasConfigValue(String configName){
		return this.properties.containsKey(configName);
	}

	@Override
	public String getConfigValue(String configName,String defaultValue){
		return this.properties.getProperty(configName,defaultValue);
	}

}