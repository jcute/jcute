package com.jcute.core.json.config.source;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcute.core.config.support.AbstractConfigSource;
import com.jcute.core.util.GenericUtils;
import com.jcute.core.util.StringUtils;

public class ConfigSourceForJson extends AbstractConfigSource{
	
	public static final String EXTENSION_JSON = "json";
	
	private Map<Object,Object> object;
	
	public ConfigSourceForJson(String fileName,InputStream inputStream){
		super(fileName,EXTENSION_JSON);
		if(null == inputStream){
			throw new IllegalArgumentException("input stream must not be null");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			this.object = GenericUtils.parse(objectMapper.readValue(inputStream,HashMap.class));
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage(),e);
		}finally{
			if(null != inputStream){
				try{
					inputStream.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public boolean hasConfigValue(String configName){
		return this.getNodeValue(configName) != null;
	}
	
	@Override
	public String getConfigValue(String configName,String defaultValue){
		String result = this.getNodeValue(configName);
		if(null == result){
			return defaultValue;
		}
		return result;
	}
	
	private String getNodeValue(String configName){
		System.out.println(this.object);
		String[] names = null;
		if(configName.indexOf(".") != -1){
			names = configName.split("\\.");
		}else{
			names = new String[]{configName};
		}
		if(null == names || names.length == 0){
			return null;
		}
		for(int i=0;i<names.length;i++){
			String name = names[i];
			if(StringUtils.isEmpty(name)){
				return null;
			}
			System.out.println(name);
		}
		return null;
	}
	
}