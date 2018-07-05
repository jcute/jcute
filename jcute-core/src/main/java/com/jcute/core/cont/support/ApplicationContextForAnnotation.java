package com.jcute.core.cont.support;

import com.jcute.core.bean.BeanDefinitionFactory;
import com.jcute.core.bean.BeanDefinitionRegistry;
import com.jcute.core.bean.BeanDefinitionResolver;
import com.jcute.core.config.ConfigSourceManager;
import com.jcute.core.plugin.PluginManager;
import com.jcute.core.plugin.support.DefaultPluginManagerForAnnotation;

public class ApplicationContextForAnnotation extends AbstractApplicationContext{

	@Override
	protected BeanDefinitionFactory createBeanDefinitionFactory(){
		return null;
	}

	@Override
	protected BeanDefinitionRegistry createBeanDefinitionRegistry(){
		return null;
	}

	@Override
	protected BeanDefinitionResolver createBeanDefinitionResolver(){
		return null;
	}

	@Override
	protected ConfigSourceManager createConfigSourceManager(){
		return null;
	}

	@Override
	protected PluginManager createPluginManager(){
		return new DefaultPluginManagerForAnnotation(this);
	}

}