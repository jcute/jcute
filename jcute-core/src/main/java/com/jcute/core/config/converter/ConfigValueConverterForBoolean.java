package com.jcute.core.config.converter;

import com.jcute.core.config.support.AbstractConfigValueConverter;

public class ConfigValueConverterForBoolean extends AbstractConfigValueConverter<Boolean>{

	@Override
	public Class<Boolean> getConverterType(){
		return Boolean.class;
	}

	@Override
	protected Boolean doConvert(String value) throws Exception{
		return Boolean.valueOf(value);
	}

}