package com.jcute.core.config;

public interface ConfigValue<T>{
	
	public T getValue();
	
	public ConfigValueFrom getConfigValueFrom();
	
	public boolean isNotFoundValue();
	
	public boolean isDefaultValue();
	
	public boolean isDefaultExpressionValue();
	
	public boolean isDefaultArgumentValue();
	
}