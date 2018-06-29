package com.jcute.core.config.converter;

import com.jcute.core.config.support.AbstractConfigValueConverter;

public class ConfigValueConverterForShort extends AbstractConfigValueConverter<Short>{

	@Override
	public Class<Short> getConverterType(){
		return Short.class;
	}

	@Override
	protected Short doConvert(String value) throws Exception{
		return Short.valueOf(value);
	}

}