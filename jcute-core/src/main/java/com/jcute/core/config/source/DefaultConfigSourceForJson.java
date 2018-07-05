package com.jcute.core.config.source;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.support.AbstractConfigSource;
import com.jcute.core.util.StringUtils;

public class DefaultConfigSourceForJson extends AbstractConfigSource{

	private JSONObject object;

	public DefaultConfigSourceForJson(String fileName,String content){
		super(fileName,ConfigSource.EXTENSION_JSON);
		if(StringUtils.isEmpty(content)){
			throw new IllegalArgumentException("json content must not be null");
		}
		this.object = JSON.parseObject(content);
		if(null == this.object){
			throw new IllegalArgumentException("json content must not be null");
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
		String[] names = null;
		if(configName.indexOf(".") != -1){
			names = configName.split("\\.");
		}else{
			names = new String[]{configName};
		}
		if(null == names || names.length == 0){
			return null;
		}
		JSONObject parent = this.object;
		for(int i = 0;i < names.length;i++){
			String name = names[i];
			if(StringUtils.isEmpty(name)){
				return null;
			}
			name = name.trim();
			if(parent.containsKey(name)){
				if(i == names.length - 1){
					return parent.getString(name);
				}else{
					parent = parent.getJSONObject(name);
				}
			}
		}
		return null;
	}

}