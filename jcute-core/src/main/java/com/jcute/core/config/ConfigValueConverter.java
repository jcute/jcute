package com.jcute.core.config;

public interface ConfigValueConverter<T>{
	
	public Class<T> getConverterType();
	
	public T convert(String value);
	
}