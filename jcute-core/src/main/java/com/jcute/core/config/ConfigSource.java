package com.jcute.core.config;

public interface ConfigSource{
	
	public static final String EXTENSION_MAP = "map";
	public static final String EXTENSION_PROPERTIES = "properties";
	public static final String EXTENSION_XML = "xml";
	
	public boolean hasConfigValue(String configName);
	
	public String getConfigFileName();
	
	public String getConfigFileExtension();
	
	public String getConfigValue(String configName);
	
	public String getConfigValue(String configName,String defaultValue);
	
}