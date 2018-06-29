package com.jcute.core.config.converter;

import com.jcute.core.config.support.AbstractConfigValueConverter;

public class ConfigValueConverterForLong extends AbstractConfigValueConverter<Long>{

	@Override
	public Class<Long> getConverterType(){
		return Long.class;
	}

	@Override
	protected Long doConvert(String value) throws Exception{
		return Long.valueOf(value);
	}

}