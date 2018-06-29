package com.jcute.core.config;

import java.net.URL;
import java.util.Properties;

import org.junit.Test;

import com.jcute.core.config.source.DefaultConfigSourceForPropertiesFile;
import com.jcute.core.config.support.DefaultConfigSourceManager;

public class TestConfigSourceManager{
	
	@Test
	public void run() throws Exception{
		
		String location = "/application.properties";
		URL url = this.getClass().getResource(location);
		Properties properties = new Properties();
		properties.load(url.openStream());
		ConfigSource configSource = new DefaultConfigSourceForPropertiesFile("application",properties);
		
		ConfigSourceManager configSourceManager = new DefaultConfigSourceManager();
		configSourceManager.attachConfigSource(configSource);
		
		System.out.println(configSourceManager.getStringValue("test.name").getValue());
		System.out.println(configSourceManager.getStringValue("hello[a:默认值](test.name)").getValue());
		
		Thread.sleep(1000);
		
	}
	
	@Test
	public void testDefaultValue() throws Exception{
		
		ConfigSourceManager configSourceManager = new DefaultConfigSourceManager();
		
		System.out.println(configSourceManager.getStringValue("test.name:测试","默认值测试").getValue());
		System.out.println(configSourceManager.getStringValue("test.name:测试").getValue());
		System.out.println(configSourceManager.getStringValue("os.name:Linux").getValue());
		
	}
	
}