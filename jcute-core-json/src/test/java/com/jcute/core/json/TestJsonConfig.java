package com.jcute.core.json;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.ImportResource;
import com.jcute.core.boot.Application;
import com.jcute.core.json.config.plugin.EnableJsonResource;

@Configuration
@EnableJsonResource
@ImportResource("config.json")
public class TestJsonConfig{
	
	public static void main(String[] args){
		Application.run(TestJsonConfig.class);
	}
	
}