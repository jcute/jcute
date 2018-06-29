package com.jcute.core.config.source;

public class DefaultConfigSourceForSystemEnvironment extends DefaultConfigSourceForMap{

	public DefaultConfigSourceForSystemEnvironment(){
		super("ENVIRONMENT",System.getenv());
	}

}