package com.jcute.core.json.config.plugin;

import java.lang.annotation.Annotation;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.config.ConfigSourceManager;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.json.config.converter.ConfigSourceConverterForJson;
import com.jcute.core.plugin.Plugin;

public class EnableJsonResourcePlugin extends Plugin{

	public EnableJsonResourcePlugin(ApplicationContext applicationContext,Annotation annotation){
		super(applicationContext,annotation);
	}

	@Override
	protected void onBeforeStart() throws Exception{
		BeanDefinitionFactory beanDefinitionFactory = this.getApplicationContext().getBeanDefinitionFactory();
		ConfigSourceManager configSourceManager = beanDefinitionFactory.getConfigSourceManager();
		configSourceManager.attachConfigSourceConverter(new ConfigSourceConverterForJson());
	}

}