package com.jcute.core.config.source;

import java.util.Map;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.support.AbstractConfigSource;

public class DefaultConfigSourceForMap extends AbstractConfigSource{

	private Map<String,String> properties;

	public DefaultConfigSourceForMap(String fileName,Map<String,String> properties){
		super(fileName,ConfigSource.EXTENSION_MAP);
		if(null == properties){
			throw new IllegalArgumentException("properties map must not be null");
		}
		this.properties = properties;
	}

	@Override
	public boolean hasConfigValue(String configName){
		return this.properties.containsKey(configName);
	}

	@Override
	public String getConfigValue(String configName,String defaultValue){
		if(this.hasConfigValue(configName)){
			return this.properties.get(configName);
		}else{
			return defaultValue;
		}
	}

}