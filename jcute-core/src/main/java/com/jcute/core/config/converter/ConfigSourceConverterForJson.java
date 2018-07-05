package com.jcute.core.config.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.source.DefaultConfigSourceForJson;
import com.jcute.core.config.support.AbstractConfigSourceConverter;

public class ConfigSourceConverterForJson extends AbstractConfigSourceConverter{

	@Override
	public String getExtension(){
		return ConfigSource.EXTENSION_JSON;
	}

	@Override
	protected ConfigSource doConvert(InputStream inputStream,String fileName) throws Exception{
		String content = this.readToString(inputStream);
		return new DefaultConfigSourceForJson(fileName,content);
	}
	
	private String readToString(InputStream inputStream) throws IOException{
		StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		while ((line = br.readLine()) != null) {
		    sb.append(line);
		}
		return sb.toString();
	}
	
}