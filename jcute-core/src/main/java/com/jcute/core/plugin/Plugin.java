package com.jcute.core.plugin;

import java.lang.annotation.Annotation;

import com.jcute.core.context.ApplicationContext;
import com.jcute.core.util.GenericUtils;

public abstract class Plugin{

	private ApplicationContext applicationContext;
	private Annotation annotation;

	public Plugin(ApplicationContext applicationContext,Annotation annotation){
		if(null == applicationContext){
			throw new IllegalArgumentException("application context must not be null");
		}
		this.applicationContext = applicationContext;
		this.annotation = annotation;
	}

	public final void start() throws Exception{
		this.onStart();
	}

	public final void beforeStart() throws Exception{
		this.onBeforeStart();
	}

	public final void beforeClose() throws Exception{
		this.onBeforeClose();
	}

	public final void close() throws Exception{
		this.onClose();
	}
	
	protected void onBeforeStart() throws Exception{

	}

	protected void onStart() throws Exception{

	}

	protected void onBeforeClose() throws Exception{

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