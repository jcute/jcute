package com.jcute.core.plugin.support;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jcute.core.annotation.Pluggable;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.plugin.Plugin;
import com.jcute.core.plugin.PluginManagerEvent;
import com.jcute.core.plugin.PluginManagerListener;
import com.jcute.core.toolkit.logging.Logger;
import com.jcute.core.toolkit.logging.LoggerFactory;
import com.jcute.core.util.AnnotationUtils;

public class DefaultPluginManagerForAnnotation extends AbstractPluginManager{

	private static final Logger logger = LoggerFactory.getLogger(DefaultPluginManagerForAnnotation.class);

	public DefaultPluginManagerForAnnotation(ApplicationContext applicationContext){
		super(applicationContext);
		this.attachPluginAddSuccessListener(new PluginManagerListener() {
			@Override
			public void execute(PluginManagerEvent event) throws Exception{
				logger.debug("add plugin success {}",event.getPlugin().getClass());
			}
		});
		this.attachPluginDelSuccessListener(new PluginManagerListener() {
			@Override
			public void execute(PluginManagerEvent event) throws Exception{
				logger.debug("del plugin success {}",event.getPlugin().getClass());
			}
		});
	}

	@Override
	public Map<Class<? extends Plugin>,Annotation> resolvePlugins(Class<?> beanType){
		Map<Class<? extends Plugin>,Annotation> result = new LinkedHashMap<Class<? extends Plugin>,Annotation>();
		if(null == beanType){
			return result;
		}
		Annotation[] annotations = beanType.getAnnotations();
		if(null == annotations || annotations.length == 0){
			return result;
		}
		for(int i = 0;i < annotations.length;i++){
			Annotation annotation = annotations[i];
			Class<?> annotationClass = annotation.annotationType();
			if(!AnnotationUtils.hasAnnotation(annotationClass,Pluggable.class)){
				continue;
			}
			Pluggable pluggable = AnnotationUtils.getAnnotation(annotationClass,Pluggable.class);
			Class<? extends Plugin> pluginClass = pluggable.value();
			if(null == pluginClass){
				continue;
			}
			result.put(pluginClass,annotation);
		}
		return result;
	}

}