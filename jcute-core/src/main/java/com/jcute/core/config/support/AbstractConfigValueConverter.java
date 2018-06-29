package com.jcute.core.config.support;

import com.jcute.core.config.ConfigValueConverter;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;

public abstract class AbstractConfigValueConverter<T> implements ConfigValueConverter<T>{
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractConfigValueConverter.class);
	
	@Override
	public T convert(String value){
		if(null == value){
			return null;
		}
		try{
			return this.doConvert(value);
		}catch(Exception e){
			logger.warn("invoke value converter failed {},default use null",e.getMessage(),e);
			return null;
		}
	}
	
	protected abstract T doConvert(String value) throws Exception;
	
}