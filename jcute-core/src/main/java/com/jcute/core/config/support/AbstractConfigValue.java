package com.jcute.core.config.support;

import com.jcute.core.config.ConfigValue;
import com.jcute.core.config.ConfigValueFrom;

public abstract class AbstractConfigValue<T> implements ConfigValue<T>{

	protected T value = null;
	protected ConfigValueFrom configValueFrom = ConfigValueFrom.NotFound;
	
	public AbstractConfigValue(T value,ConfigValueFrom configValueFrom){
		this.value = value;
		this.configValueFrom = configValueFrom;
	}
	
	@Override
	public T getValue(){
		return this.value;
	}

	@Override
	public ConfigValueFrom getConfigValueFrom(){
		return this.configValueFrom;
	}

	@Override
	public boolean isNotFoundValue(){
		return this.configValueFrom == ConfigValueFrom.NotFound;
	}
	
	@Override
	public boolean isDefaultValue(){
		return this.configValueFrom != ConfigValueFrom.Definition;
	}

	@Override
	public boolean isDefaultExpressionValue(){
		return this.configValueFrom == ConfigValueFrom.Expression;
	}

	@Override
	public boolean isDefaultArgumentValue(){
		return this.configValueFrom == ConfigValueFrom.Argument;
	}

}