package com.jcute.core.boot;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.Initial;
import com.jcute.core.annotation.Property;

@Configuration
public class BootStrap{
	
	@Property("os.name")
	private String name;
	
	@Initial
	public void init(){
		System.out.println(this.name);
	}
	
	public static void main(String[] args){
		Application.run(BootStrap.class);
	}
	
}