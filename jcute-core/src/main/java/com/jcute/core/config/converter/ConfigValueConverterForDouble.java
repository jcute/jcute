package com.jcute.core.config.converter;

import com.jcute.core.config.support.AbstractConfigValueConverter;

public class ConfigValueConverterForDouble extends AbstractConfigValueConverter<Double>{

	@Override
	public Class<Double> getConverterType(){
		return Double.class;
	}

	@Override
	protected Double doConvert(String value) throws Exception{
		return Double.valueOf(value);
	}

}