package com.jcute.core.config.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.config.ConfigName;
import com.jcute.core.config.ConfigValue;
import com.jcute.core.config.ConfigValueFrom;
import com.jcute.core.config.converter.ConfigSourceConverterForJson;
import com.jcute.core.config.converter.ConfigSourceConverterForProperties;
import com.jcute.core.config.converter.ConfigSourceConverterForXml;
import com.jcute.core.config.converter.ConfigValueConverterForBigDecimal;
import com.jcute.core.config.converter.ConfigValueConverterForBoolean;
import com.jcute.core.config.converter.ConfigValueConverterForClass;
import com.jcute.core.config.converter.ConfigValueConverterForDate;
import com.jcute.core.config.converter.ConfigValueConverterForDouble;
import com.jcute.core.config.converter.ConfigValueConverterForFloat;
import com.jcute.core.config.converter.ConfigValueConverterForInteger;
import com.jcute.core.config.converter.ConfigValueConverterForLong;
import com.jcute.core.config.converter.ConfigValueConverterForShort;
import com.jcute.core.config.converter.ConfigValueConverterForString;
import com.jcute.core.config.source.DefaultConfigSourceForSystemEnvironment;
import com.jcute.core.config.source.DefaultConfigSourceForSystemProperties;
import com.jcute.core.util.GenericUtils;

public class DefaultConfigSourceManager extends AbstractConfigSourceManager{

	private Map<ConfigName,ConfigValue<?>> caches = new ConcurrentHashMap<ConfigName,ConfigValue<?>>();

	@Override
	protected void doStart() throws Exception{
		this.attachConfigSourceConverter(new ConfigSourceConverterForProperties());
		this.attachConfigSourceConverter(new ConfigSourceConverterForXml());
		this.attachConfigSourceConverter(new ConfigSourceConverterForJson());
		
		this.attachConfigValueConverter(new ConfigValueConverterForBoolean());
		this.attachConfigValueConverter(new ConfigValueConverterForDouble());
		this.attachConfigValueConverter(new ConfigValueConverterForFloat());
		this.attachConfigValueConverter(new ConfigValueConverterForInteger());
		this.attachConfigValueConverter(new ConfigValueConverterForLong());
		this.attachConfigValueConverter(new ConfigValueConverterForShort());
		this.attachConfigValueConverter(new ConfigValueConverterForString());
		this.attachConfigValueConverter(new ConfigValueConverterForBigDecimal());
		this.attachConfigValueConverter(new ConfigValueConverterForDate());
		this.attachConfigValueConverter(new ConfigValueConverterForClass());

		this.attachConfigSource(new DefaultConfigSourceForSystemEnvironment());
		this.attachConfigSource(new DefaultConfigSourceForSystemProperties());
	}

	@Override
	protected void doClose() throws Exception{
		this.caches.clear();
		this.configSourceConverters.clear();
		this.configValueConverters.clear();
		this.configSources.clear();
	}
	
	@Override
	protected ConfigName createConfigName(String configName){
		return new DefaultConfigName(configName);
	}

	@Override
	protected <T>ConfigValue<T> createConfigValue(ConfigName configName,T resultValue,ConfigValueFrom configValueFrom){
		if(this.caches.containsKey(configName)){
			return GenericUtils.parse(this.caches.get(configName));
		}
		ConfigValue<T> configValue = super.createConfigValue(configName,resultValue,configValueFrom);
		if(null != resultValue && configValueFrom == ConfigValueFrom.Definition){
			this.caches.put(configName,configValue);
		}
		return configValue;
	}

}