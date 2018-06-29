package com.jcute.core.config.converter;

import com.jcute.core.config.support.AbstractConfigValueConverter;

public class ConfigValueConverterForString extends AbstractConfigValueConverter<String>{

	@Override
	public Class<String> getConverterType(){
		return String.class;
	}

	@Override
	protected String doConvert(String value) throws Exception{
		return value;
	}

}