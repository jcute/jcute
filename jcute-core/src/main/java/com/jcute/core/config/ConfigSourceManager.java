package com.jcute.core.config;

import java.io.InputStream;
import java.util.Map;

public interface ConfigSourceManager{

	public void attachConfigSource(ConfigSource configSource);

	public void attachConfigSource(InputStream inputStream,String fileName,String fileExtension);

	public void attachConfigSourceConverter(ConfigSourceConverter configSourceConverter);

	public void attachConfigValueConverter(ConfigValueConverter<?> configValueConverter);

	public Map<String,ConfigSource> getConfigSources();

	public Map<Class<?>,ConfigValueConverter<?>> getConfigValueConverters();

	public Map<String,ConfigSourceConverter> getConfigSourceConverters();

	public ConfigValue<Integer> getIntegerValue(String configName,Integer defaultValue);

	public ConfigValue<Integer> getIntegerValue(String configName);

	public ConfigValue<String> getStringValue(String configName,String defaultValue);

	public ConfigValue<String> getStringValue(String configName);

	public ConfigValue<Boolean> getBooleanValue(String configName,Boolean defaultValue);

	public ConfigValue<Boolean> getBooleanValue(String configName);

	public ConfigValue<Short> getShortValue(String configName,Short defaultValue);

	public ConfigValue<Short> getShortValue(String configName);

	public ConfigValue<Long> getLongValue(String configName,Long defaultValue);

	public ConfigValue<Long> getLongValue(String configName);

	public ConfigValue<Double> getDoubleValue(String configName,Double defaultValue);

	public ConfigValue<Double> getDoubleValue(String configName);

	public ConfigValue<Float> getFloatValue(String configName,Float defaultValue);

	public ConfigValue<Float> getFloatValue(String configName);

	public <T>ConfigValue<T> getValue(Class<T> valueType,String configName,T defaultValue);

	public <T>ConfigValue<T> getValue(Class<T> valueType,String configName);

}