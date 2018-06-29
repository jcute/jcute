package com.jcute.core.config.converter;

import java.math.BigDecimal;

import com.jcute.core.config.support.AbstractConfigValueConverter;

public class ConfigValueConverterForBigDecimal extends AbstractConfigValueConverter<BigDecimal>{

	@Override
	public Class<BigDecimal> getConverterType(){
		return BigDecimal.class;
	}
	
	@Override
	protected BigDecimal doConvert(String value) throws Exception{
		return new BigDecimal(value);
	}
	
}