package com.jcute.core.config.support;

import com.jcute.core.config.ConfigName;
import com.jcute.core.util.StringUtils;

public abstract class AbstractConfigName implements ConfigName{
	
	protected String originalConfigName;
	protected String realConfigName;
	protected String configFile;
	protected String prefixName;
	protected String defaultValue;
	
	public AbstractConfigName(String originalConfigName){
		if(StringUtils.isEmpty(originalConfigName)){
			throw new IllegalArgumentException("config original name must not be null");
		}
		this.originalConfigName = originalConfigName;
		this.resolve();
	}
	
	@Override
	public String getOriginalConfigName(){
		return this.originalConfigName;
	}

	@Override
	public String getRealConfigName(){
		return this.realConfigName;
	}

	@Override
	public String getRealAbsoluteConfigName(){
		if(this.hasPrefixName()){
			return String.format("%s%s",this.getPrefixName(),this.getRealConfigName());
		}else{
			return this.getRealConfigName();
		}
	}

	@Override
	public String getConfigFile(){
		return this.configFile;
	}

	@Override
	public String getDefaultValue(){
		return this.defaultValue;
	}

	@Override
	public String getPrefixName(){
		return this.prefixName;
	}
	
	@Override
	public boolean hasDefaultValue(){
		return StringUtils.isEmpty(this.defaultValue) == false;
	}

	@Override
	public boolean hasConfigFile(){
		return StringUtils.isEmpty(this.configFile) == false;
	}

	@Override
	public boolean hasPrefixName(){
		return StringUtils.isEmpty(this.prefixName) == false;
	}
	
	@Override
	public int hashCode(){
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(null == obj){
			return false;
		}
		if(obj instanceof AbstractConfigName){
			return this.toString().equals(obj.toString());
		}
		return false;
	}

	@Override
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		if(this.hasConfigFile()){
			stringBuffer.append(this.configFile).append("[");
		}
		stringBuffer.append(this.realConfigName);
		if(this.hasDefaultValue()){
			stringBuffer.append(":").append(this.defaultValue);
		}
		if(this.hasConfigFile()){
			stringBuffer.append("]");
		}
		if(this.hasPrefixName()){
			stringBuffer.append("(").append(this.prefixName).append(")");
		}
		return stringBuffer.toString();
	}

	protected abstract void resolve();
	
}