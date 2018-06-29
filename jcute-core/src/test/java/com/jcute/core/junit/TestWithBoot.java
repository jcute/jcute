package com.jcute.core.junit;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.jcute.core.annotation.Property;
import com.jcute.core.boot.BootStrap;

@RunWith(JCuteRunner.class)
@RunWithBoot(BootStrap.class)
public class TestWithBoot{
	
	@Property("os:123")
	private String name;
	
	@Test
	public void run(){
		System.out.println(this.name);
	}
	
}