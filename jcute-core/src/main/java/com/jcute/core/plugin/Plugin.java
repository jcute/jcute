package com.jcute.core.plugin;

import java.lang.annotation.Annotation;

import com.jcute.core.context.ApplicationContext;
import com.jcute.core.toolkit.cycle.support.AbstractStable;
import com.jcute.core.util.GenericUtils;

public abstract class Plugin extends AbstractStable<PluginEvent,PluginListener>{

	private ApplicationContext applicationContext;
	private Annotation annotation;

	public Plugin(ApplicationContext applicationContext,Annotation annotation){
		if(null == applicationContext){
			throw new IllegalArgumentException("application context must not be null");
		}
		this.applicationContext = applicationContext;
		this.annotation = annotation;
	}

	@Override
	protected PluginEvent createEvent(){
		return new PluginEvent() {
			@Override
			public Plugin getPlugin(){
				return Plugin.this;
			}
		};
	}

	@Override
	protected final void doStart() throws Exception{
		this.onStart();
	}

	@Override
	protected final void doClose() throws Exception{
		this.onClose();
	}

	protected void onStart() throws Exception{

	}

	protected void onClose() throws Exception{

	}

	protected <T extends Annotation>T getAnnotation(){
		return GenericUtils.parse(this.annotation);
	}

	protected ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}

}