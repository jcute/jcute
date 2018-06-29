package com.jcute.core.config.source;


public class DefaultConfigSourceForSystemProperties extends DefaultConfigSourceForPropertiesFile{

	public DefaultConfigSourceForSystemProperties(){
		super("SYSTEM",System.getProperties());
	}

}