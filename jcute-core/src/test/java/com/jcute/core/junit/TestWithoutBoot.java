package com.jcute.core.junit;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.Property;

@RunWith(JCuteRunner.class)
@Configuration
public class TestWithoutBoot{
	
	@Property("os.name")
	private String name;
	
	@Test
	public void run(){
		System.out.println(this.name);
	}
	
}