package com.jcute.core.json.config.converter;

import java.io.InputStream;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.support.AbstractConfigSourceConverter;
import com.jcute.core.json.config.source.ConfigSourceForJson;

public class ConfigSourceConverterForJson extends AbstractConfigSourceConverter{

	@Override
	public String getExtension(){
		return ConfigSourceForJson.EXTENSION_JSON;
	}

	@Override
	protected ConfigSource doConvert(InputStream inputStream,String fileName) throws Exception{
		return new ConfigSourceForJson(fileName,inputStream);
	}

}