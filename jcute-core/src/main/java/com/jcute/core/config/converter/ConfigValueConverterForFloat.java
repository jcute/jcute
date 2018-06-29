package com.jcute.core.config.converter;

import com.jcute.core.config.support.AbstractConfigValueConverter;

public class ConfigValueConverterForFloat extends AbstractConfigValueConverter<Float>{

	@Override
	public Class<Float> getConverterType(){
		return Float.class;
	}

	@Override
	protected Float doConvert(String value) throws Exception{
		return Float.valueOf(value);
	}

}