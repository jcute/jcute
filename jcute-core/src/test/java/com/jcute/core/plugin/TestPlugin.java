package com.jcute.core.plugin;

import org.junit.Test;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.context.support.ApplicationContextForAnnotation;

@Configuration
@EnableLoggerPlugin(level="自定义级别")
public class TestPlugin{
	
	@Test
	public void run() throws Exception{
		ApplicationContext applicationContext = new ApplicationContextForAnnotation(TestPlugin.class);
		applicationContext.start();
		Thread.sleep(1000);
		applicationContext.close();
	}
	
}