package com.jcute.core.config;

/**
 * 属性名格式对象:fileName[properties.key:defaultValue](prefix)
 */
public interface ConfigName{
	
	//获取原始configName
	public String getOriginalConfigName();
	
	//获取真实文件名
	public String getRealConfigName();
	
	public String getRealAbsoluteConfigName();
	
	public String getConfigFile();
	
	public String getDefaultValue();
	
	public String getPrefixName();
	
	public boolean hasDefaultValue();
	
	public boolean hasConfigFile();
	
	public boolean hasPrefixName();
	
}