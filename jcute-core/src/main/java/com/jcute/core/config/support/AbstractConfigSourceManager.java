package com.jcute.core.config.support;

import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.jcute.core.config.ConfigName;
import com.jcute.core.config.ConfigSource;
import com.jcute.core.config.ConfigSourceConverter;
import com.jcute.core.config.ConfigSourceManager;
import com.jcute.core.config.ConfigValue;
import com.jcute.core.config.ConfigValueConverter;
import com.jcute.core.config.ConfigValueFrom;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.util.GenericUtils;

public abstract class AbstractConfigSourceManager implements ConfigSourceManager{

	private static final Logger logger = LoggerFactory.getLogger(AbstractConfigSourceManager.class);

	private static int NULL_VALUE_INT;
	private static short NULL_VALUE_SHORT;
	private static long NULL_VALUE_LONG;
	private static double NULL_VALUE_DOUBLE;
	private static float NULL_VALUE_FLOAT;
	private static boolean NULL_VALUE_BOOLEAN;
	private static char NULL_VALUE_CHAR;
	private static byte NULL_VALUE_BYTE;

	protected Map<String,ConfigSource> configSources = new ConcurrentHashMap<String,ConfigSource>();
	protected Map<Class<?>,ConfigValueConverter<?>> configValueConverters = new ConcurrentHashMap<Class<?>,ConfigValueConverter<?>>();
	protected Map<String,ConfigSourceConverter> configSourceConverters = new ConcurrentHashMap<String,ConfigSourceConverter>();

	@Override
	public void attachConfigSource(ConfigSource configSource){
		if(null == configSource){
			logger.warn("config source must not be null");
			return;
		}
		String key = String.format("%s.%s",configSource.getConfigFileName(),configSource.getConfigFileExtension());
		if(this.configSources.containsKey(key)){
			logger.warn("config source has same name with [{}.{}]",configSource.getConfigFileName(),configSource.getConfigFileExtension());
			return;
		}
		this.configSources.put(key,configSource);
		logger.debug("attach config source success [{}]",configSource.toString());
	}

	@Override
	public void attachConfigSource(InputStream inputStream,String fileName,String fileExtension){
		if(null == inputStream){
			logger.warn("input stream must not be null");
			return;
		}
		if(null == fileName){
			logger.warn("file name must not be null");
			return;
		}
		if(null == fileExtension){
			logger.warn("file extension must not be null");
			return;
		}
		if(fileName.indexOf(fileExtension) != -1){
			fileName = fileName.substring(0,fileName.indexOf(fileExtension) - 1);
		}
		if(!this.configSourceConverters.containsKey(fileExtension)){
			logger.warn("un support config source for [{}]",fileExtension);
			return;
		}
		String key = String.format("%s.%s",fileName,fileExtension);
		if(this.configSources.containsKey(key)){
			logger.debug("ignore config source exists with [{}.{}]",fileName,fileExtension);
			return;
		}
		try{
			ConfigSource configSource = this.configSourceConverters.get(fileExtension).convert(inputStream,fileName);
			if(null == configSource){
				logger.warn("config source convert failed");
			}
			this.configSources.put(key,configSource);
			logger.debug("attach config source success [{}]",configSource.toString());
		}catch(Exception e){
			logger.warn("config source convert failed {}",e.getMessage(),e);
		}
	}

	@Override
	public void attachConfigSourceConverter(ConfigSourceConverter configSourceConverter){
		if(null == configSourceConverter){
			logger.warn("config source converter must not be null");
			return;
		}
		if(this.configSourceConverters.containsKey(configSourceConverter.getExtension())){
			logger.warn("config source converter exists [{}]",configSourceConverter.getExtension());
			return;
		}
		this.configSourceConverters.put(configSourceConverter.getExtension(),configSourceConverter);
		logger.debug("attach config source converter success [{}]",configSourceConverter.getExtension());
	}

	@Override
	public void attachConfigValueConverter(ConfigValueConverter<?> configValueConverter){
		if(null == configValueConverter){
			logger.warn("config value converter must not be null");
			return;
		}
		if(this.configValueConverters.containsKey(configValueConverter.getConverterType())){
			logger.warn("config value converter exists [{}]",configValueConverter.getConverterType());
			return;
		}
		this.configValueConverters.put(configValueConverter.getConverterType(),configValueConverter);
		logger.debug("attach config value converter success [{}]",configValueConverter.getConverterType());
	}

	@Override
	public Map<String,ConfigSource> getConfigSources(){
		return this.configSources;
	}

	@Override
	public Map<Class<?>,ConfigValueConverter<?>> getConfigValueConverters(){
		return this.configValueConverters;
	}

	@Override
	public Map<String,ConfigSourceConverter> getConfigSourceConverters(){
		return this.configSourceConverters;
	}

	@Override
	public ConfigValue<Integer> getIntegerValue(String configName,Integer defaultValue){
		return this.getValue(Integer.class,configName,defaultValue);
	}

	@Override
	public ConfigValue<Integer> getIntegerValue(String configName){
		return this.getValue(Integer.class,configName);
	}

	@Override
	public ConfigValue<String> getStringValue(String configName,String defaultValue){
		return this.getValue(String.class,configName,defaultValue);
	}

	@Override
	public ConfigValue<String> getStringValue(String configName){
		return this.getValue(String.class,configName);
	}

	@Override
	public ConfigValue<Boolean> getBooleanValue(String configName,Boolean defaultValue){
		return this.getValue(Boolean.class,configName,defaultValue);
	}

	@Override
	public ConfigValue<Boolean> getBooleanValue(String configName){
		return this.getValue(Boolean.class,configName);
	}

	@Override
	public ConfigValue<Short> getShortValue(String configName,Short defaultValue){
		return this.getValue(Short.class,configName,defaultValue);
	}

	@Override
	public ConfigValue<Short> getShortValue(String configName){
		return this.getValue(Short.class,configName);
	}

	@Override
	public ConfigValue<Long> getLongValue(String configName,Long defaultValue){
		return this.getValue(Long.class,configName,defaultValue);
	}

	@Override
	public ConfigValue<Long> getLongValue(String configName){
		return this.getValue(Long.class,configName);
	}

	@Override
	public ConfigValue<Double> getDoubleValue(String configName,Double defaultValue){
		return this.getValue(Double.class,configName,defaultValue);
	}

	@Override
	public ConfigValue<Double> getDoubleValue(String configName){
		return this.getValue(Double.class,configName);
	}

	@Override
	public ConfigValue<Float> getFloatValue(String configName,Float defaultValue){
		return this.getValue(Float.class,configName,defaultValue);
	}

	@Override
	public ConfigValue<Float> getFloatValue(String configName){
		return this.getValue(Float.class,configName);
	}

	@Override
	public <T>ConfigValue<T> getValue(Class<T> valueType,String configName){
		return this.getValue(valueType,configName,null);
	}

	@Override
	public <T>ConfigValue<T> getValue(Class<T> valueType,String configName,T defaultValue){
		if(null == valueType){
			return this.createConfigValue(null,null,ConfigValueFrom.NotFound);
		}
		if(null == this.configSources || this.configSources.size() == 0){
			return this.createConfigValue(null,null,ConfigValueFrom.NotFound);
		}
		ConfigName configNameObject = null;
		try{
			configNameObject = this.createConfigName(configName);
		}catch(Exception e){
			return this.createConfigValue(null,null,ConfigValueFrom.NotFound);
		}
		try{
			String configValue = this.findConfigSourceValue(configNameObject.getRealAbsoluteConfigName());
			if(null == configValue){
				if(null != defaultValue){
					return this.createConfigValue(configNameObject,defaultValue,ConfigValueFrom.Argument);
				}
				if(configNameObject.hasDefaultValue()){
					T resultValue = this.convertValue(valueType,configNameObject.getDefaultValue());
					if(null != resultValue){
						return this.createConfigValue(configNameObject,resultValue,ConfigValueFrom.Expression);
					}
				}
				return this.createConfigValue(configNameObject,null,ConfigValueFrom.NotFound);
			}
			T resultValue = this.convertValue(valueType,configValue);
			return this.createConfigValue(configNameObject,resultValue,ConfigValueFrom.Definition);
		}catch(Exception e){
			return this.createConfigValue(configNameObject,null,ConfigValueFrom.NotFound);
		}
	}

	protected <T>ConfigValue<T> createConfigValue(ConfigName configName,T resultValue,ConfigValueFrom configValueFrom){
		return new AbstractConfigValue<T>(resultValue,configValueFrom) {};
	}

	protected <T>T convertValue(Class<T> valueType,String value){
		if(int.class.equals(valueType)){
			try{
				return GenericUtils.parse(Integer.valueOf(value));
			}catch(Exception e){
				return GenericUtils.parse(NULL_VALUE_INT);
			}
		}else if(short.class.equals(valueType)){
			try{
				return GenericUtils.parse(Short.valueOf(value));
			}catch(Exception e){
				return GenericUtils.parse(NULL_VALUE_SHORT);
			}
		}else if(long.class.equals(valueType)){
			try{
				return GenericUtils.parse(Long.valueOf(value));
			}catch(Exception e){
				return GenericUtils.parse(NULL_VALUE_LONG);
			}
		}else if(double.class.equals(valueType)){
			try{
				return GenericUtils.parse(Double.valueOf(value));
			}catch(Exception e){
				return GenericUtils.parse(NULL_VALUE_DOUBLE);
			}
		}else if(float.class.equals(valueType)){
			try{
				return GenericUtils.parse(Float.valueOf(value));
			}catch(Exception e){
				return GenericUtils.parse(NULL_VALUE_FLOAT);
			}
		}else if(boolean.class.equals(valueType)){
			try{
				return GenericUtils.parse(Boolean.valueOf(value));
			}catch(Exception e){
				return GenericUtils.parse(NULL_VALUE_BOOLEAN);
			}
		}else if(char.class.equals(valueType)){
			try{
				return GenericUtils.parse(value.charAt(0));
			}catch(Exception e){
				return GenericUtils.parse(NULL_VALUE_CHAR);
			}
		}else if(byte.class.equals(valueType)){
			try{
				return GenericUtils.parse(Byte.parseByte(value));
			}catch(Exception e){
				return GenericUtils.parse(NULL_VALUE_BYTE);
			}
		}
		if(this.configValueConverters.containsKey(valueType)){
			return GenericUtils.parse(this.configValueConverters.get(valueType).convert(value));
		}
		return null;
	}

	protected String findConfigSourceValue(String configName){
		for(Entry<String,ConfigSource> entry : this.configSources.entrySet()){
			ConfigSource configSource = entry.getValue();
			if(configSource.hasConfigValue(configName)){
				return configSource.getConfigValue(configName);
			}
		}
		return null;
	}

	protected abstract ConfigName createConfigName(String configName);

}