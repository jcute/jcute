package com.jcute.core.config.support;

import com.jcute.core.util.StringUtils;

public class DefaultConfigName extends AbstractConfigName{

	public DefaultConfigName(String originalConfigName){
		super(originalConfigName);
	}

	@Override
	protected void resolve(){
		int index = -1;
		String configName = this.originalConfigName;
		String prefix = "";
		int startIndex = configName.indexOf("(");
		int endIndex = configName.indexOf(")");
		if(startIndex + 1 == endIndex){
			configName = configName.substring(0,startIndex);
		}else{
			if(startIndex != -1 && endIndex != -1 && startIndex < endIndex){
				prefix = configName.substring(startIndex + 1);
				prefix = prefix.substring(0,prefix.indexOf(")"));
				configName = configName.substring(0,startIndex);
				if(!prefix.endsWith(".")){
					prefix += ".";
				}
				this.prefixName = prefix;
			}else{
				this.prefixName = "";
			}
		}
		if((index = configName.indexOf(":")) != -1){
			this.realConfigName = configName.substring(0,index);
			this.defaultValue = configName.substring(index + 1);
		}else{
			this.realConfigName = configName;
		}
		if((index = this.realConfigName.indexOf("[")) != -1){
			this.configFile = this.realConfigName.substring(0,index);
			this.realConfigName = this.realConfigName.substring(index + 1);
			if(null != this.defaultValue && this.defaultValue.endsWith("]")){
				this.defaultValue = this.defaultValue.substring(0,this.defaultValue.length() - 1);
			}
			if(this.realConfigName.endsWith("]")){
				this.realConfigName = this.realConfigName.substring(0,this.realConfigName.length() - 1);
			}
		}
		if(StringUtils.isEmpty(this.realConfigName)){
			throw new IllegalArgumentException(String.format("config name format error %s",this.originalConfigName));
		}
	}

}