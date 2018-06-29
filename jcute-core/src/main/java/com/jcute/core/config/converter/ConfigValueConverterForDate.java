package com.jcute.core.config.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jcute.core.config.support.AbstractConfigValueConverter;

public class ConfigValueConverterForDate extends AbstractConfigValueConverter<Date>{

	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SIMPLE_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Class<Date> getConverterType(){
		return Date.class;
	}

	@Override
	protected Date doConvert(String value) throws Exception{
		if(value.length() <= 10){
			return SIMPLE_DATE_FORMAT.parse(value);
		}else{
			return SIMPLE_DATETIME_FORMAT.parse(value);
		}
	}

}