package com.jcute.core.config.converter;

import com.jcute.core.config.support.AbstractConfigValueConverter;


public class ConfigValueConverterForInteger extends AbstractConfigValueConverter<Integer>{

	@Override
	public Class<Integer> getConverterType(){
		return Integer.class;
	}
	
	@Override
	protected Integer doConvert(String value) throws Exception{
		return Integer.valueOf(value);
	}
	
}