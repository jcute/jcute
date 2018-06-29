package com.jcute.core.config.converter;

import com.jcute.core.config.support.AbstractConfigValueConverter;
import com.jcute.core.util.GenericUtils;

public class ConfigValueConverterForClass extends AbstractConfigValueConverter<Class<?>>{
	
	@Override
	protected Class<?> doConvert(String value) throws Exception{
		return Class.forName(value);
	}

	@Override
	public Class<Class<?>> getConverterType(){
		return GenericUtils.parse(Class.class);
	}

}