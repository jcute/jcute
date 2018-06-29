package com.jcute.core.context;

import org.junit.Test;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.ImportResource;
import com.jcute.core.context.support.ApplicationContextForAnnotation;

@Configuration
@ImportResource({"application.xml","application.properties"})
public class TestAnnotationApplicationContext{

	@Test
	public void testRun() throws Exception{

		ApplicationContext applicationContext = new ApplicationContextForAnnotation(TestAnnotationApplicationContext.class);
		applicationContext.start();

//		System.out.println(applicationContext.getConfigSourceManager().getStringValue("os.name").getValue());
		
//		System.out.println(applicationContext.getConfigSourceManager().getStringValue("include").getValue());
//		System.out.println(applicationContext.getConfigSourceManager().getStringValue("config.server.port").getValue());
		
//		System.out.println(applicationContext);
//
		TestService testService = applicationContext.getBean("testService");
		System.out.println(testService.getName());
//		System.out.println(applicationContext.getBean("testService"));
//		System.out.println(applicationContext.getBean("testService"));

		applicationContext.close();

	}

}